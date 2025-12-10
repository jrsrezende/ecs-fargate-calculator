data "aws_availability_zones" "available" {
  state = "available"
}

resource "aws_vpc" "main" {
  cidr_block           = "10.0.0.0/16"
  enable_dns_support   = true
  enable_dns_hostnames = true

  tags = {
    Name = "Fargate-Calculator-VPC"
  }
}

locals {
  subnets = {
    public_a  = { cidr = "10.0.1.0/24", az_index = 0, public = true }
    public_b  = { cidr = "10.0.2.0/24", az_index = 1, public = true }
    private_a = { cidr = "10.0.11.0/24", az_index = 0, public = false }
    private_b = { cidr = "10.0.12.0/24", az_index = 1, public = false }
  }
}

resource "aws_subnet" "main" {
  for_each                = local.subnets
  vpc_id                  = aws_vpc.main.id
  cidr_block              = each.value.cidr
  availability_zone       = data.aws_availability_zones.available.names[each.value.az_index]
  map_public_ip_on_launch = each.value.public

  tags = {
    Name = "${title(each.key)}-Fargate-Calculator"
  }
}

resource "aws_internet_gateway" "main" {
  vpc_id = aws_vpc.main.id
  tags = {
    Name = "Fargate-Calculator-IGW"
  }
}

resource "aws_eip" "nat" {
  domain = "vpc"
}

resource "aws_nat_gateway" "main" {
  allocation_id = aws_eip.nat.id
  subnet_id     = aws_subnet.main["public_a"].id

  tags = {
    Name = "Fargate-Calculator-NAT"
  }
  depends_on = [aws_internet_gateway.main]
}

resource "aws_route_table" "public" {
  vpc_id = aws_vpc.main.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.main.id
  }
  tags = {
    Name = "Fargate-Calculator-Public-RT"
  }
}

resource "aws_route_table" "private" {
  vpc_id = aws_vpc.main.id
  route {
    cidr_block     = "0.0.0.0/0"
    nat_gateway_id = aws_nat_gateway.main.id
  }
  tags = {
    Name = "Fargate-Calculator-Private-RT"
  }
}

resource "aws_route_table_association" "main" {
  for_each       = aws_subnet.main
  subnet_id      = each.value.id
  route_table_id = startswith(each.key, "public") ? aws_route_table.public.id : aws_route_table.private.id
}
