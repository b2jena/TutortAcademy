<img src="https://r2cdn.perplexity.ai/pplx-full-logo-primary-dark%402x.png" class="logo" width="120"/>

## When and Where to Use Abstract Classes vs. Interfaces for Abstraction in Java

### Overview

Abstraction separates the *what* (interface) of an object from the *how* (implementation). In Java, you can achieve
abstraction with both **abstract classes** and **interfaces**, but they serve slightly different purposes and are chosen
based on design requirements.

## 1. Interfaces

### Use Interfaces When:

1. **Defining a Contract Across Unrelated Classes**
    - You need multiple, disparate classes to share a common set of behaviors without imposing a class hierarchy.
    - Example: `Comparable`, `Runnable`, `Serializable`—any class, regardless of its inheritance chain, can implement
      these.
2. **Supporting Multiple Inheritance of Type**
    - A class can implement many interfaces, allowing it to be treated polymorphically in different roles.
    - Example:

```java
public interface Auditable {
    void audit();
}

public interface Secure {
    void authenticate();
}

public class PaymentService implements Auditable, Secure { /*…*/
}
```

3. **Defining Purely Abstract Behavior**
    - When you only need method signatures (and, since Java 8, default or static helper methods), without any instance
      fields or constructor logic.
    - Example:

```java
public interface Shape {
    double area();

    double perimeter();

    default void log() {
        System.out.println("Shape measured");
    }
}
```

### Where to Add Interfaces

- **API Definitions:** Expose interfaces in your module’s public API so consumers depend on abstractions, not
  implementations.
- **Service Layer Contracts:** Define service contracts (e.g., `UserService`, `OrderService`) as interfaces and provide
  concrete implementations in separate classes.

## 2. Abstract Classes

### Use Abstract Classes When:

1. **Sharing Common Code and State**
    - You want to define both method signatures *and* provide some reusable base logic or maintain common state (
      fields).
    - Example:

```java
public abstract class Vehicle {
    protected String id;

    public Vehicle(String id) {
        this.id = id;
    }

    public abstract void start();

    public void stop() {
        System.out.println("Vehicle stopped");
    }
}
```

2. **Partial Implementation**
    - Some methods have a default implementation, while others remain abstract for subclasses to override.
    - Example:

```java
public abstract class DataProcessor {
    public void process() {
        readData();
        transformData();
        writeData();
    }

    protected abstract void readData();

    protected abstract void transformData();

    protected void writeData() {
        System.out.println("Writing data to default sink");
    }
}
```

3. **Controlled Inheritance**
    - You want to restrict all subclasses to share a common ancestor and possibly prevent classes outside the hierarchy
      from implementing the interface.
    - Example:

```java
public abstract class BaseRepository { /* … */
}

public class UserRepository extends BaseRepository { /* … */
}
```

### Where to Add Abstract Classes

- **Base Frameworks and Libraries:** Provide core functionality and let library users extend for custom behaviors.
- **Template Method Patterns:** Define the algorithm’s skeleton in the abstract class and leave details to subclasses.
- **Shared Utility Code:** When multiple closely related classes share helper methods and fields.

## 3. Comparison \& Decision Guide

| Criteria                    | Interface                                             | Abstract Class                                   |
|:----------------------------|:------------------------------------------------------|:-------------------------------------------------|
| **Multiple Inheritance**    | Yes (a class can implement many interfaces)           | No (only single class inheritance)               |
| **Default Implementations** | Yes (via `default` methods since Java 8)              | Yes (regular methods)                            |
| **State (Fields)**          | Cannot hold instance fields (only constants)          | Can have instance fields                         |
| **Constructor**             | No constructors                                       | Can define constructors                          |
| **When to Use**             | Define a contract or role across unrelated types      | Share code and state among closely related types |
| **Backward Compatibility**  | Can add `default` methods without breaking subclasses | Adding abstract methods will break subclasses    |

## 4. Practical Examples

### Interface Example

```java
public interface Notification {
    void send(String message);

    default void log(String message) {
        System.out.println("Logging: " + message);
    }
}

public class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        // Email-specific send logic
    }
}

public class SmsNotification implements Notification {
    @Override
    public void send(String message) {
        // SMS-specific send logic
    }
}
```

### Abstract Class Example

```java
public abstract class NotificationSender {
    protected String senderId;

    public NotificationSender(String senderId) {
        this.senderId = senderId;
    }

    // Shared helper
    protected void validate(String message) {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }
    }

    // Template method
    public final void execute(String message) {
        validate(message);
        sendInternal(message);
        audit(message);
    }

    protected abstract void sendInternal(String message);

    // Shared audit logic
    private void audit(String message) {
        System.out.println("Audited by " + senderId + ": " + message);
    }
}

public class PushNotificationSender extends NotificationSender {
    public PushNotificationSender(String senderId) {
        super(senderId);
    }

    @Override
    protected void sendInternal(String message) {
        // Push-specific logic
    }
}
```

## 5. Key Takeaways

- **Use interfaces** when you need to define a broad contract, support multiple inheritance of type, and keep
  implementations completely decoupled.
- **Use abstract classes** when you need to share code, enforce a common base state, or apply the template method
  pattern among closely related classes.
- Design for **flexibility**: start with interfaces where possible; resort to abstract classes when shared
  implementation is required.

