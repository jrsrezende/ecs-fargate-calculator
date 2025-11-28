resource "aws_security_group" "alb" {
  name        = "fargate-calculator-alb-sg"
  description = "ALB: Public access on port 443 and outbound traffic to ECS."
  vpc_id      = aws_vpc.main.id
  tags = {
    Name = "Fargate-Calculator-ALB-SG"
  }
}

resource "aws_security_group_rule" "alb_ingress_https" {
  type              = "ingress"
  from_port         = 443
  to_port           = 443
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.alb.id
  description       = "Allows inbound HTTPS (443)"
}

resource "aws_security_group_rule" "alb_ingress_http" {
  type              = "ingress"
  from_port         = 80
  to_port           = 80
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.alb.id
  description       = "Allows inbound HTTP (80) for HTTPS redirect."
}

resource "aws_security_group_rule" "alb_egress_frontend" {
  type                     = "egress"
  from_port                = 80
  to_port                  = 80
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.frontend_ecs.id
  security_group_id        = aws_security_group.alb.id
  description              = "Outbound traffic to the Frontend (80)."
}

resource "aws_security_group_rule" "alb_egress_backend" {
  type                     = "egress"
  from_port                = 8080
  to_port                  = 8080
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.backend_ecs.id
  security_group_id        = aws_security_group.alb.id
  description              = "Outbound traffic to the Backend (8080)."
}

resource "aws_security_group" "frontend_ecs" {
  name        = "fargate-calculator-frontend-sg"
  vpc_id      = aws_vpc.main.id
  description = "ECS Frontend: Receives traffic only from the ALB on port 80."
  tags = {
    Name = "Fargate-Calculator-Frontend-SG"
  }
}

resource "aws_security_group_rule" "frontend_ingress" {
  type                     = "ingress"
  from_port                = 80
  to_port                  = 80
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.alb.id
  security_group_id        = aws_security_group.frontend_ecs.id
  description              = "Allows traffic on port 80 coming ONLY from the ALB."
}

resource "aws_security_group_rule" "frontend_egress_internet" {
  type              = "egress"
  from_port         = 443
  to_port           = 443
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.frontend_ecs.id
  description       = "Allows outbound traffic to the internet (443) for ECR and CloudWatch (via NAT Gateway)."
}

resource "aws_security_group" "backend_ecs" {
  name        = "fargate-calculator-backend-sg"
  vpc_id      = aws_vpc.main.id
  description = "ECS Backend: Receives traffic only from the ALB on port 8080."
  tags = {
    Name = "Fargate-Calculator-Backend-SG"
  }
}

resource "aws_security_group_rule" "backend_ingress" {
  type                     = "ingress"
  from_port                = 8080
  to_port                  = 8080
  protocol                 = "tcp"
  source_security_group_id = aws_security_group.alb.id
  security_group_id        = aws_security_group.backend_ecs.id
  description              = "Allows traffic on port 8080 coming ONLY from the ALB."
}

resource "aws_security_group_rule" "backend_egress_internet" {
  type              = "egress"
  from_port         = 443
  to_port           = 443
  protocol          = "tcp"
  cidr_blocks       = ["0.0.0.0/0"]
  security_group_id = aws_security_group.backend_ecs.id
  description       = "Allows outbound traffic to DynamoDB and AWS Services (443) (via NAT Gateway)."
}