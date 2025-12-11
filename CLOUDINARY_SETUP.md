# Configuración de Cloudinary para Fotos de Perfil

Este documento explica cómo configurar Cloudinary para el almacenamiento de fotos de perfil en la aplicación MyClub.

## ¿Por qué Cloudinary?

Railway y otros servicios en la nube tienen **sistemas de archivos efímeros**, lo que significa que cualquier archivo guardado localmente se pierde cuando se hace un nuevo deploy. Cloudinary soluciona este problema almacenando las imágenes en la nube de forma permanente.

## Paso 1: Crear cuenta en Cloudinary

1. Ve a [https://cloudinary.com/users/register_free](https://cloudinary.com/users/register_free)
2. Regístrate con tu email (es **gratis** hasta 25GB de almacenamiento)
3. Verifica tu cuenta por email

## Paso 2: Obtener credenciales

1. Inicia sesión en [https://cloudinary.com/console](https://cloudinary.com/console)
2. En el Dashboard verás tres credenciales importantes:
   - **Cloud Name** (nombre de tu nube)
   - **API Key** (clave de API)
   - **API Secret** (secreto de API - ¡mantenlo privado!)

![Cloudinary Dashboard](https://res.cloudinary.com/demo/image/upload/docs/cloudinary_dashboard.png)

## Paso 3: Configurar variables de entorno en Railway

### Opción A: Desde la interfaz web de Railway

1. Ve a tu proyecto en [https://railway.app](https://railway.app)
2. Selecciona tu servicio de backend (API)
3. Ve a la pestaña **"Variables"**
4. Agrega las siguientes variables:

   ```
   CLOUDINARY_CLOUD_NAME=tu-cloud-name
   CLOUDINARY_API_KEY=tu-api-key
   CLOUDINARY_API_SECRET=tu-api-secret
   ```

5. Haz click en **"Deploy"** para aplicar los cambios

### Opción B: Usando Railway CLI

```bash
railway variables set CLOUDINARY_CLOUD_NAME=tu-cloud-name
railway variables set CLOUDINARY_API_KEY=tu-api-key
railway variables set CLOUDINARY_API_SECRET=tu-api-secret
```

## Paso 4: Desarrollo local

Para desarrollo local, crea un archivo `.env` en la raíz del proyecto backend (NO lo subas a Git):

```properties
CLOUDINARY_CLOUD_NAME=tu-cloud-name
CLOUDINARY_API_KEY=tu-api-key
CLOUDINARY_API_SECRET=tu-api-secret
```

O simplemente reemplaza los valores en `application.properties` temporalmente (no hagas commit de esto).

## Paso 5: Hacer deploy

1. **Asegúrate de que las variables de entorno estén configuradas en Railway**
2. Haz commit y push de los cambios del código:

   ```bash
   git add .
   git commit -m "Feat: Integrar Cloudinary para almacenamiento de fotos de perfil"
   git push
   ```

3. Railway automáticamente detectará los cambios y hará el deploy

## Verificar que funciona

1. Inicia sesión en tu aplicación
2. Ve a tu perfil
3. Sube una nueva foto de perfil
4. La foto debería subirse correctamente y verse en tu perfil
5. Verifica en el Dashboard de Cloudinary que la imagen aparece en la carpeta `myclub/fotos-perfil`

## Migrar fotos antiguas (Opcional)

Las fotos que subiste cuando tenías el sistema de archivos local ya no están disponibles. Para que los usuarios vuelvan a tener foto de perfil, tienen dos opciones:

1. **Opción manual**: Cada usuario puede volver a subir su foto de perfil
2. **Opción automática**: Puedes escribir un script para migrar las fotos desde tu backup local a Cloudinary

### Script de migración (ejemplo)

Si tienes las fotos antiguas en tu ordenador local, puedes crear un script Java para subirlas:

```java
// Ejemplo básico - adaptar según necesidades
public void migrarFotosACloudinary() {
    // 1. Listar todas las fotos en la carpeta local 'uploads/fotos-perfil'
    // 2. Para cada foto:
    //    - Leerla como byte[]
    //    - Subirla a Cloudinary usando cloudinaryService.subirImagen()
    //    - Actualizar la URL en la base de datos
}
```

## Límites del plan gratuito de Cloudinary

- ✅ 25 GB de almacenamiento
- ✅ 25 GB de ancho de banda mensual
- ✅ Transformaciones de imágenes ilimitadas
- ✅ CDN global

Para la mayoría de aplicaciones pequeñas/medianas, esto es más que suficiente.

## Troubleshooting

### Error: "Invalid API credentials"
- Verifica que copiaste correctamente las credenciales
- Asegúrate de que no haya espacios adicionales
- Revisa que las variables de entorno estén configuradas en Railway

### Las imágenes no se ven
- Verifica en el Dashboard de Cloudinary que las imágenes se subieron
- Revisa la consola del navegador para errores de CORS
- Asegúrate de que la URL de la imagen sea accesible públicamente

### Error: "File size too large"
- El límite actual es 5MB por imagen (configurado en application.properties)
- Puedes aumentarlo si es necesario

## Soporte

Si tienes problemas:
1. Revisa la documentación oficial: [https://cloudinary.com/documentation](https://cloudinary.com/documentation)
2. Revisa los logs en Railway
3. Verifica que las variables de entorno estén correctamente configuradas
