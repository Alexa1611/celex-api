# Desplegar CELEX en Render

## Antes de empezar

1. Cuenta en [render.com](https://render.com) (conectar con GitHub)
2. En Yugabyte → clúster **celex1** → **Network Access** → permitir **0.0.0.0/0**  
   (Render usa IPs variables; necesario para que el backend en la nube conecte a la BD)

## 1. Backend (celex-api)

1. Render Dashboard → **New +** → **Web Service**
2. Conectar repo: `Alexa1611/celex-api`
3. Configuración:
   - **Name:** `celex-api`
   - **Runtime:** Docker
   - **Plan:** Free
4. **Environment Variables** (Environment):

   | Key | Value |
   |-----|-------|
   | `YUGABYTE_JDBC_URL` | `jdbc:postgresql://mx-central-1.275770c1-adc4-489e-a5d8-f3f3be165d99.aws.yugabyte.cloud:5433/yugabyte?sslmode=require&connectTimeout=60&socketTimeout=60` |
   | `YUGABYTE_DB_USER` | `alexa1celex` |
   | `YUGABYTE_DB_PASSWORD` | *(tu contraseña)* |

5. **Create Web Service** → esperar deploy (~5-10 min)
6. Probar: `https://celex-api.onrender.com/swagger-ui.html`

## 2. Frontend (celex-frontend)

1. Editar `src/environments/environment.prod.ts` → poner la URL real del backend
2. Commit y push a GitHub
3. Render → **New +** → **Static Site**
4. Repo: `Alexa1611/celex-frontend`
5. Configuración:
   - **Build Command:** `npm install && npm run build`
   - **Publish Directory:** `dist/angular-frases/browser`
6. **Create Static Site**
7. Probar: `https://celex-frontend.onrender.com`

## Notas

- El plan free de Render **duerme** tras 15 min sin uso; la primera carga tarda ~1 min
- Swagger y frontend deben usar la misma URL del backend desplegado
- No subas contraseñas a GitHub; solo en variables de entorno de Render
