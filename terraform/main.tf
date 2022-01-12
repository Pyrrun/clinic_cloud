# Configure the Azure provider
terraform {
  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "~> 2.65"
    }
  }

  required_version = ">= 1.1.0"
}

provider "azurerm" {
  features {}
}

resource "azurerm_resource_group" "rg" {
  name     = "myTFResourceGroup"
  location = "West Europe"
}

resource "azurerm_storage_account" "sa" {
  name                     = "clinic"
  resource_group_name      = azurerm_resource_group.rg.name
  location                 = azurerm_resource_group.rg.location
  account_tier             = "Standard"
  account_replication_type = "LRS"
}

resource "azurerm_mssql_server" "sqlserver" {
  name                         = "clinicmssqlserver"
  resource_group_name          = azurerm_resource_group.rg.name
  location                     = azurerm_resource_group.rg.location
  version                      = "12.0"
  administrator_login          = "admin123"
  administrator_login_password = "Password123"
  minimum_tls_version          = "1.2"

  tags = {
    environment = "chmura"
  }
}

resource "azurerm_mssql_database" "clinicdb" {
  name           = "clinic"
  server_id      = azurerm_mssql_server.sqlserver.id
  collation      = "SQL_Latin1_General_CP1_CI_AS"
  license_type   = "LicenseIncluded"
  read_scale     = false
  max_size_gb    = 1
  sku_name       = "Basic"
  zone_redundant = false
}

resource "azurerm_kubernetes_cluster" "default" {
  name                = "mithrilclinic-aks"
  location            = azurerm_resource_group.rg.location
  resource_group_name = azurerm_resource_group.rg.name
  dns_prefix          = "mithrilclinic-k8s"

  default_node_pool {
    name            = "default"
    node_count      = 1
    vm_size         = "Standard_B2s"
    os_disk_size_gb = 30
  }

  role_based_access_control {
    enabled = true
  }

  identity {
    type = "SystemAssigned"
  }

  tags = {
    environment = "chmura"
  }
}

resource "azurerm_container_registry" "acr" {
  name                = "acrmithrilclinic"
  resource_group_name = azurerm_resource_group.rg.name
  location            = azurerm_resource_group.rg.location
  sku                 = "Standard"
  admin_enabled       = true
}

resource "azurerm_role_assignment" "kubweb_to_acr" {
  scope                = azurerm_container_registry.acr.id
  role_definition_name = "AcrPull"
  principal_id         = azurerm_kubernetes_cluster.default.kubelet_identity[0].object_id
}

resource "azurerm_sql_firewall_rule" "sqlfirewall" {
  name                = "sqlserver01"
  resource_group_name = azurerm_resource_group.rg.name
  server_name         = azurerm_mssql_server.sqlserver.name
  start_ip_address    = "0.0.0.0"
  end_ip_address      = "255.255.255.255"
}

resource "azurerm_dns_zone" "public" {
  name                = "mithrilclinic.com"
  resource_group_name = azurerm_resource_group.rg.name
}
