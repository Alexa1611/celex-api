# Documentación de base de datos — CELEX

Entregables de la rúbrica del proyecto:

| Archivo | Contenido |
|---------|-----------|
| [DICCIONARIO_DE_DATOS.md](./DICCIONARIO_DE_DATOS.md) | Diccionario de datos de las 5 entidades |
| [MODELO_ER.md](./MODELO_ER.md) | Modelo entidad-relación (diagrama Mermaid) |
| [schema.sql](./schema.sql) | Script de creación de tablas (YugabyteDB/PostgreSQL) |

## Cómo ejecutar el script

En Yugabyte Cloud Shell o `ysqlsh`:

```sql
\i schema.sql
```

O copia y pega el contenido de `schema.sql` en el editor SQL del clúster **celex1**.
