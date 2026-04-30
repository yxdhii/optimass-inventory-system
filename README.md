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

## 🖥️ Capturas del sistema

<div align="center">

**Menú Principal**
![Menu](assets/menu.png)

<br/>

**Módulo de Productos**
![Productos](assets/productos.png)

<br/>

**Catálogo de Productos**
![Catalogo](assets/catalogo.png)

<br/>

**Módulo de Inventario**
![Inventario](assets/inventario.png)

<br/>

**Módulo de Ventas**
![Ventas](assets/ventas.png)

<br/>

**Módulo de Compras**
![Compras](assets/compras.png)

<br/>

**Módulo de Categorías**
![Categorias](assets/categorias.png)

<br/>

**Módulo de Proveedores**
![Proveedores](assets/proveedores.png)

</div>

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

## 🛠️ Stack tecnológico

- **Lenguaje:** Java · Java Swing
- **Base de datos:** MySQL · MySQL Workbench
- **Conector:** JDBC
- **IDE:** Apache NetBeans
- **Gráficas:** JFreeChart
- **Arquitectura:** MVC

---

## 🎓 Contexto académico

> Proyecto desarrollado para el curso de **Algoritmos y Estructura de Datos**  
> Facultad de Ingeniería — Ingeniería de Sistemas e Informática y Software  
> **Universidad Tecnológica del Perú · 2025**  
> Docente: Mg. Milton Freddy Amache Sánchez · Grupo 04

---

<div align="center">

Desarrollado por **Yadhira Patricia Saavedra Guadalupe**

*Lima, Perú · 2025*

</div>
