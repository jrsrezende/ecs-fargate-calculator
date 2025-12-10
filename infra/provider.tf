terraform {
  required_version = ">= 1.5.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 6.0"
    }
  }

  backend "s3" {
    bucket = "tf-state-calculator"
    key = "ecs/calculator.tfstate"
    region = "us-east-1"
    encrypt = true
    dynamodb_table = "terraform-lock-table"
  }
}

provider "aws" {
  region = "us-east-1"
  default_tags {
    tags = {
      Project     = "ECS-Calculator"
      ManagedBy   = "Terraform"
      Environment = "Lab"
    }
  }
}
