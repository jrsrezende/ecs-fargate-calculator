variable "backend_image_tag" {
  description = "Dynamic Backend tag"
  type        = string
  default     = "latest"
}

variable "frontend_image_tag" {
  description = "Dynamic Frontend tag"
  type        = string
  default     = "latest"
}