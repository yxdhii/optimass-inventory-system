<div align="center">

<br/>

# 🛒 OptiMass
### Sistema de Control de Inventario — Tiendas Mass

<br/>

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![NetBeans](https://img.shields.io/badge/NetBeans-1B6AC6?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)
![Arquitectura](https://img.shields.io/badge/Arquitectura-MVC-orange?style=for-the-badge)
![Estado](https://img.shields.io/badge/Estado-Completado-brightgreen?style=for-the-badge)

<br/>

> *"Todo bajo control."*

<br/>

![Login](assets/login.png)

</div>

---

## 📌 Descripción

**OptiMass** es un sistema de escritorio desarrollado para optimizar la gestión de inventario de las Tiendas Mass. Centraliza el control de productos, ventas, compras y personal en una sola aplicación, eliminando los registros manuales y reduciendo errores operativos.

Como característica diferencial, incorpora **verificación de acceso por correo electrónico**, reforzando la seguridad del sistema con un flujo moderno de recuperación de contraseña.

---

# 🖥️ Galería del Sistema

| 🏠 Menú Principal | 📦 Productos |
|-------------------|--------------|
| <img src="assets/menu.png" width="450"/> | <img src="assets/productos.png" width="450"/> |

| 🍕 Catálogo | 📊 Inventario |
|-------------|---------------|
| <img src="assets/catalogo.png" width="450"/> | <img src="assets/inventario.png" width="450"/> |

| 💰 Ventas | 🛒 Compras |
|-----------|------------|
| <img src="assets/ventas.png" width="450"/> | <img src="assets/compras.png" width="450"/> |

| 🗂️ Categorías | 🚚 Proveedores |
|----------------|----------------|
| <img src="assets/categorias.png" width="450"/> | <img src="assets/proveedores.png" width="450"/> |

---

## ⚙️ Módulos del sistema

| Módulo | Descripción |
|---|---|
| 🛒 **Productos** | Registro con imagen, búsqueda, catálogo por categorías y exportación a Excel |
| 🏷️ **Categorías** | 8 categorías predefinidas, exportación a PDF y Excel |
| 🚚 **Proveedores** | Registro y administración con búsqueda y exportación |
| 📦 **Inventario** | Seguimiento de stock actual, stock mínimo y estado en tiempo real |
| 💳 **Ventas** | Registro de ventas diarias con cálculo automático de totales |
| 📥 **Compras** | Pedidos a proveedores con actualización automática del inventario |
| 👥 **Empleados** | Gestión de personal con roles, salario y exportación |
| 📊 **Reportes** | Gráficas de inventario actual y productos con bajo stock |
| ⚙️ **Configuración** | Cambio de contraseña, alertas automáticas y gestión de roles |

---

## ✨ Características destacadas

- 🔐 Inicio de sesión seguro con código Mass y contraseña
- 📧 Recuperación de contraseña mediante código de verificación enviado al correo
- 🔔 Alertas automáticas de bajo stock y productos próximos a vencer
- 📤 Exportación de reportes en PDF y Excel
- 👤 Control de acceso por roles: Administrador, Supervisor y Vendedor
- 🖼️ Catálogo visual de productos organizado por categorías

---

## 🏗️ Arquitectura MVC

El sistema fue desarrollado siguiendo el patrón **Modelo–Vista–Controlador**:

```
Gestion_Inventario_Mass/
├── src/main/
│   ├── conexion/        → Conexión a base de datos (JDBC + MySQL)
│   ├── controlador/     → Lógica de negocio
│   ├── dao/             → Acceso a datos
│   ├── modelo/          → Clases del dominio
│   ├── vista/           → Interfaces gráficas (Java Swing)
│   └── util/            → Utilidades generales
└── resources/
    └── imagenes/        → Íconos e imágenes del sistema
```

---

## 👤 Roles de usuario

| Rol | Acceso |
|---|---|
| 👑 **Administrador** | Todos los módulos (9/9) |
| 🔍 **Supervisor** | Todos los módulos (hereda permisos del Administrador) |
| 🏪 **Vendedor** | Productos, Ventas y Configuración |

---

## 🔑 Flujo de recuperación de contraseña

```
Login  →  Restablecer Contraseña
              ↓
        Ingresar correo asociado
              ↓
        Recibir código por email
              ↓
        Ingresar código de verificación
              ↓
        Crear nueva contraseña ✓
```

---

# 🛠️ Stack Tecnológico

<div align="center">

### 💻 Desarrollo

<img src="https://skillicons.dev/icons?i=java,mysql,git,github,vscode" />

<br><br>

<img src="https://img.shields.io/badge/Java_Swing-007396?style=for-the-badge&logo=openjdk&logoColor=white"/>
<img src="https://img.shields.io/badge/JDBC-4A90E2?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Apache_NetBeans-1B6AC6?style=for-the-badge&logo=apachenetbeanside&logoColor=white"/>
<img src="https://img.shields.io/badge/MySQL_Workbench-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/>
<img src="https://img.shields.io/badge/JFreeChart-FF9800?style=for-the-badge"/>
<img src="https://img.shields.io/badge/MVC-8E44AD?style=for-the-badge"/>

</div>

---

## 🎓 Contexto académico

> Proyecto desarrollado para el curso de **Algoritmos y Estructura de Datos**  
> Facultad de Ingeniería — Ingeniería de Sistemas e Informática   
> **Universidad Tecnológica del Perú · 2025**  
> Docente: Mg. Milton Freddy Amache Sánchez

---

<div align="center">

## 👩🏻‍💻 Desarrollado por

### **Yadhira Patricia Saavedra Guadalupe**

Estudiante de **Ingeniería de Sistemas**  
Universidad Tecnológica del Perú (UTP)

<br>

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/itsyxdhi/)
[![Portafolio](https://img.shields.io/badge/Portafolio-FF4D8D?style=for-the-badge&logo=vercel&logoColor=white)](https://yadhira-portafolio.vercel.app)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/yxdhii)

<br>


© 2025 · Optimass - System

</div>
