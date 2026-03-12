# 🏦 L3GL Money - API de Portefeuille Numérique

[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-blue)](https://www.docker.com/)
[![MIT License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

## 🎯 Présentation

**L3GL Money** est une API Spring Boot simulant un portefeuille électronique pour démontrer les concepts d'**observabilité**, de **monitoring** et de **DevOps**.

### Objectifs
- 💰 Simulation d'opérations financières (comptes, transferts, dépôts)
- 📊 Démonstration d'observabilité avec Prometheus, Grafana, ELK
- 🔍 Tracing distribué avec OpenTelemetry
- 🐳 Containerisation avec Docker

## ⭐ Fonctionnalités

| Endpoint | Méthode | Description |
|----------|---------|-------------|
| `/api/accounts` | POST | Créer un compte |
| `/api/accounts` | GET | Lister les comptes |
| `/api/cashin` | POST | Dépôt d'argent |
| `/api/transfer` | POST | Transfert entre comptes |
| `/api/transactions` | GET | Historique des transactions |

## 🛠 Technologies

- **Backend**: Java 21, Spring Boot, Spring Data JPA, Lombok
- **Base de données**: MySQL
- **Observabilité**: Prometheus, Grafana, ELK Stack, OpenTelemetry
- **Déploiement**: Docker, Docker Compose

## 🏗 Architecture
Client → API Spring Boot → MySQL Database ↓ Observabilité Stack: Logs → ELK | Metrics → Prometheus → Grafana | Traces → OpenTelemetry


## 🚀 Démarrage Rapide
### Prérequis
- Java 21+
- Maven 3.8+
- Docker & Docker Compose


## 👨‍💻 Auteur

**Mohamed Cherif Diallo** - Backend Developer  
📧 chediallo99@gmail.com | 🐙 [@chediallo99](https://github.com/Chediallo)

## 📄 Licence

Ce projet est sous licence **MIT**.

---
⭐ **Star ce projet si il vous aide !** ⭐
