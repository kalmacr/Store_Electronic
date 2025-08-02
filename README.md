# 🛒 TechShop

**TechShop** es una aplicación web para la gestión de productos tecnológicos en una tienda en línea. Permite administrar categorías, productos, imágenes, precios y existencias, con una interfaz intuitiva y un backend robusto.

---

## 🚀 Características principales

- 📦 Gestión de productos: alta, modificación, eliminación y listado.
- 🖼️ Carga de imágenes localmente con rutas dinámicas.
- 🗂️ Organización por categorías (ej. Drones, DJI).
- 📊 Visualización de precios, existencias y estado activo.
- 🔐 Panel de administración con autenticación.
- 🌐 Interfaz web responsiva y amigable.

---

## 🧰 Tecnologías utilizadas

| Componente       | Tecnología                          |
|------------------|-------------------------------------|
| Backend          | Java + Spring Boot                  |
| Base de datos    | MySQL (Aiven Cloud)             |
| Frontend         | Thymeleaf + HTML/CSS                |
| Almacenamiento   | Local file system (/opt/techshop)
              (temporalmente reemplaza Firebase Storage) |
| Despliegue       | Docker + Render                 
| Control de versiones | GitHub                          |

---

## 🗃️ Estructura del proyecto
techshop/
├── src/
│   ├── main/
│   │   ├── java/com/techshop/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── model/
│   │   │   └── repository/
│   │   └── resources/templates/
├── imagenes/
│   └── producto/
├── Dockerfile
└── README.md

