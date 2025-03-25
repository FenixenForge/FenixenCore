# Fenixen Core

## Introducción
Fenixen Core es un framework basado en Bukkit que facilita la creación de plugins de Minecraft de manera rápida y sencilla. Proporciona herramientas para:
- Registro semi-automático de comandos, eventos, inventarios e items.
- Soporte para PlaceholderAPI.
- Conexión y gestión de bases de datos en **SQLite** y **MySQL**.
- Manejo de archivos **YAML** de forma estructurada.
- Manejo de colores personalizados en mensajes y consola.

---

## Instalación
Para comenzar a usar Fenixen Core en tu plugin de Bukkit/Spigot, agrega la dependencia en tu proyecto:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.FenixenForge</groupId>
    <artifactId>FenixenCore</artifactId>
    <version>2.0.1</version>
</dependency>
```

**Requisitos:**
- Java 17 o superior.
- Bukkit, Spigot.
- PlaceholderAPI (si se desea usar placeholders).

---

## Uso del Framework

### Registro de Comandos
Fenixen Core permite registrar comandos de manera rápida y sencilla en tu **Main.java**:

```java
import com.fenixenforge.Core.Handlers.Commands.Commands;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    
    @Override
    public void onEnable() {
        Commands.RegisterAll(this, "Commands", true);
    }
}
```

### Creación de un Comando

```java
import com.fenixenforge.Core.Handlers.Commands.MCBuilder;

public class MainCommand extends MCBuilder {
    public MainCommand() {
        name("main")
        .permission("pluginname.main")
        .aliases("m", "Mains")
        .execute((sender, command, label, args) -> {
            sender.sendMessage("Hola Mundo 3");
            return true;
        });
    }
}
```

### Creación de un SubComando

```java
import com.fenixenforge.Core.Handlers.Commands.SCBuilder;

public class SubMainCommand extends SCBuilder {
    public SubMainCommand() {
        name("submain")
        .cPrincipalName("main")
        .permission("pluginname.main.submain")
        .execute((sender, command, label, args) -> {
            sender.sendMessage("SubComando ejecutado");
            return true;
        });
    }
}
```

---

## Eventos
### Registro de Eventos
Registra eventos en el **Main.java**:

```java
import com.fenixenforge.Core.Handlers.Events.Events;

@Override
public void onEnable() {
    Events.RegisterAll(this, "Events", true);
}
```

### Creación de un Evento

```java
import com.fenixenforge.Core.Handlers.Events.EHandlerBuilder;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class JoinEventHandler extends EHandlerBuilder {
    public JoinEventHandler() {
        event(PlayerJoinEvent.class)
            .execute(event -> {
                Player player = event.getPlayer();
                player.sendMessage("Bienvenido al servidor!");
            });
    }
}
```

---

## Inventarios
### Creación de un Inventario

```java
import com.fenixenforge.Core.Handlers.Inventory.IBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CustomInventory {
    public void openInventory(Player player) {
        IBuilder.inventario()
        .name("menuInventario")
        .size(27)
        .title("&a&lMenú de Inventario")
        .items(inv -> inv.setItem(13, new org.bukkit.inventory.ItemStack(Material.DIAMOND, 1)))
        .open(player);
    }
}
```

---

## YAML (Configuraciones)
### Registro de Archivos YAML

```java
import com.fenixenforge.Core.Handlers.Yaml.YHandler;

@Override
public void onEnable() {
    YHandler.RegisterAll(this, "Yaml");
}
```

### Creación de Archivos YAML

```java
import com.fenixenforge.Core.Handlers.Yaml.YamlFile;
import org.bukkit.configuration.file.FileConfiguration;

public class Config implements YamlFile {
    private static FileConfiguration config;

    public static void init(FileConfiguration conf) {
        config = conf;
    }

    public static String getMessage() {
        return config.getString("message", "Hola Mundo");
    }
}
```

---

## Placeholders
### Registro de Placeholders

```java
import com.fenixenforge.Core.Handlers.Placeholders.PlaceholderBuilder;
import org.bukkit.entity.Player;

public class MyPlaceholders {
    public void register() {
        PlaceholderBuilder.placeholder()
            .identificator("player_name")
            .funcion(Player::getName)
            .register();
    }
}
```

---

## Base de Datos
### Conexión a SQLite/MySQL

```java
import com.fenixenforge.Core.Handlers.Database.DBuilder;
import com.fenixenforge.Core.Handlers.Database.DBTypes;
import com.fenixenforge.Core.Handlers.Database.DHandler;

@Override
public void onEnable() {
    DHandler dHandler = DBuilder.database()
        .name("MyDB")
        .type(DBTypes.SQLITE)
        .path("plugins/MyPlugin/db.sqlite")
        .connect();
}
```

### Creación de Tablas

```java
import com.fenixenforge.Core.Handlers.Database.TableBuilder;

public class UserTable {
    private final String createSQL = TableBuilder.table()
        .name("users")
        .addColumn("uuid", "TEXT", true, true)
        .addColumn("username", "TEXT")
        .addColumn("coins", "INTEGER", "0")
        .buildCreateTableSQL();
}
```

### Operaciones con Base de Datos

```java
// Insertar
DatabaseAPI.table("users").insert()
    .value("uuid", player.getUniqueId().toString())
    .value("username", player.getName())
    .value("coins", 0)
    .execute();

// Seleccionar
ResultSet rs = DatabaseAPI.table("users").select()
    .columns("username", "coins")
    .where("uuid", player.getUniqueId().toString())
    .executeQuery();
```

---

## Conclusión
Fenixen Core proporciona una estructura modular y flexible para el desarrollo de plugins en Bukkit/Spigot. Su implementación sencilla y su organización estructurada permiten a los desarrolladores centrarse en la lógica de sus plugins sin preocuparse por la configuración tediosa.

Si necesitas ayuda, revisa la documentación oficial o pregunta en la comunidad de desarrollo de Fenixen Forge.

---

**Enlaces útiles:**
- [Repositorio de Fenixen Core](https://github.com/FenixenForge/FenixenCore)
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)

