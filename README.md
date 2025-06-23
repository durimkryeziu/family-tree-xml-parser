# 🌳 Family Tree XML Parser 🤖

A Spring Boot application that parses XML files representing family tree entries and persists them into a relational database. It validates the input, constructs a tree structure, and stores entries in an `ENTRY` table with parent-child relationships.

---

## ✨ Features

- **📂 XML Parsing**: Reads and unmarshals XML into domain objects using **JAXB**.  
- **✅ Validation**: Ensures exactly one root entry and at least one root before processing.  
- **🌲 Tree Construction**: Builds a hierarchical `TreeNode` structure from flat XML entries.  
- **💾 Persistence**: Saves nodes recursively into the database with parent-child links using **JDBC** and **Flyway** migrations.  
- **⏰ Scheduled Processing**: Monitors a configurable directory for new XML files and processes them automatically.  
- **🚀 REST API**: Exposes a `POST /documents` endpoint to submit XML payloads directly.  
- **⚠️ Error Handling**: Returns meaningful HTTP 400 responses for validation errors.  

---

## 🚀 Technologies

| Technology        | Version               |
|-------------------|-----------------------|
| Java              | 8                     |
| Spring Boot       | 1.5.9.RELEASE         |
| JAXB              | Built-In              |
| H2 / JDBC DB      | 2.2.220 (H2 default)  |
| Flyway            | 4.2.0                 |
| HikariCP          | 2.7.2                 |
| Testing           | JUnit, Mockito, PowerMock |
| CI / Coverage     | Travis CI, Jacoco, Coveralls |

---

## 📁 Directory Structure

```
├── LICENSE
├── README.md
├── pom.xml
├── .travis.yml
└── src
    ├── main
    │   ├── java/com/programmingskillz
    │   │   ├── FamilyTreeXmlParserApplication.java
    │   │   └── familytreexmlparser
    │   │       ├── api
    │   │       ├── application
    │   │       ├── domain
    │   │       └── infrastructure
    │   └── resources
    │       ├── application.yml
    │       ├── application-dev.yml
    │       ├── application-test.yml
    │       └── db/migration/V1__Create_schema.sql
    └── test
        └── java/com/programmingskillz
```

---

## 🔧 Prerequisites

- **Java 8** or higher  
- **Maven 3.3+**  
- (Optional) An external database (e.g., **MySQL**, **PostgreSQL**). By default, the embedded **H2** database is used.  

---

## 🛠️ Getting Started

1. **Clone the repository**  
   ```bash
   git clone https://github.com/durimkryeziu/family-tree-xml-parser.git
   cd family-tree-xml-parser
   ```

2. **Configure the database** (optional)  
   By default, the application uses an embedded H2 file-based database.  
   To use another database, set the following env vars:
   ```properties
   JDBC_URL=jdbc:postgresql://localhost:5432/familytree
   USERNAME=dbuser
   PASSWORD=dbpass
   ```
   Flyway will handle schema creation automatically.

3. **Build the project**  
   ```bash
   mvn clean package
   ```

4. **Run the application**  
   ```bash
   mvn spring-boot:run
   ```
   The application starts on **port 8080** by default. 🚀

---

## ⚙️ Configuration

All configurable properties are in `src/main/resources/application.yml`. Override per profile:

- **application-dev.yml**: H2 file, debug logging.  
- **application-test.yml**: Test profile for integration tests.

### 🔑 Key Properties

| Property                                  | Description                           | Default                                    |
|-------------------------------------------|---------------------------------------|--------------------------------------------|
| `spring.datasource.url`                   | JDBC URL for the database             | `jdbc:h2:file:~/h2_data/family-tree-entries` |
| `checking-parameters.input-directory`     | Directory to watch for XML files      | `/default-directory`                       |
| `checking-parameters.processing-interval` | Delay between scans (ms)              | `30000`                                    |

---

## 📬 Usage

### REST Endpoint

- **URL**: `POST /documents`  
- **Consumes**: `application/xml`  
- **Produces**: `application/json`

#### Sample Request
```xml
<entries>
  <entry>Adam</entry>
  <entry parentName="Adam">Stjepan</entry>
  <entry parentName="Stjepan">Luka</entry>
  <entry parentName="Adam">Leopold</entry>
</entries>
```

#### Curl Example
```bash
curl -X POST http://localhost:8080/documents      -H "Content-Type: application/xml"      -d @sample-entries.xml
```

#### ✅ Successful Response
```json
{
  "message": "Document inserted successfully"
}
```

#### ❌ Validation Errors
- Missing root entry  
- More than one root entry  

```json
{
  "message": "<error details>"
}
```

---

## 🗂️ Scheduled File Processing

1. Drop your `.xml` files into the configured **input-directory**.  
2. The app scans and processes valid XML files at intervals.  
3. Errors are logged; invalid files are skipped.

---

## 🧪 Testing

- **Unit Tests**:  
  ```bash
  mvn test
  ```  
- **Coverage Report**:  
  Generated via Jacoco and published to Coveralls.

---

## 🔄 Continuous Integration

Configured with **Travis CI**:
- Build  
- Test  
- Coverage report

---

## 📜 License

Released under the **Unlicense** ([LICENSE](LICENSE)) — free for public use in any form. 🌟
