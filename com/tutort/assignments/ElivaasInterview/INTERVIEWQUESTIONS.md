# Java Basics and Data Structures

## What is the `new` keyword?

The `new` keyword in Java is a fundamental part of the language used to create new objects. When you use the `new`
keyword, it performs the following actions:

1. **Allocates Memory**: Allocates memory for the new object on the heap.
2. **Calls Constructor**: Invokes the constructor of the class to initialize the new object.
3. **Returns Reference**: Returns a reference to the newly created object.

### Example:

```java
MyClass obj = new MyClass();
In this example, `new MyClass()` creates a new instance of `MyClass`, calls its constructor, and assigns the reference to the variable `obj`.

## What are the different ways of creating an object in Java?
There are several ways to create an object in Java, but we'll focus on two primary methods: using the `new` keyword and deserialization.

### 1. Using the `new` Keyword
This is the most common way to create an object. It involves directly invoking the constructor of the class.
```java
MyClass obj = new MyClass();
```

- **Pros**: Simple and straightforward.
- **Cons**: Requires knowledge of the class constructor and its parameters.

### 2. Using Deserialization

Deserialization is the process of converting a byte stream back into a copy of the object. This is typically used when
reading objects from a file or network.

```java
ObjectInputStream in = new ObjectInputStream(new FileInputStream("objectData.ser"));
MyClass obj = (MyClass) in.readObject();
```

- **Pros**: Useful for reconstructing objects from persistent storage or network.
- **Cons**: Requires the class to implement `Serializable` interface and handle potential `IOException` and
  `ClassNotFoundException`.

## What's the difference between stack and queue?

Stacks and queues are both abstract data types that store collections of elements, but they differ in how elements are
added and removed.

### Stack

- **Principle**: Last-In-First-Out (LIFO).
- **Operations**:
    - `push()`: Adds an element to the top of the stack.
    - `pop()`: Removes and returns the top element of the stack.
    - `peek()`: Returns the top element without removing it.
- **Use Cases**: Function call management, undo mechanisms in text editors, depth-first search algorithms.

### Queue

- **Principle**: First-In-First-Out (FIFO).
- **Operations**:
    - `enqueue()`: Adds an element to the rear of the queue.
    - `dequeue()`: Removes and returns the front element of the queue.
    - `peek()`: Returns the front element without removing it.
- **Use Cases**: Task scheduling, breadth-first search algorithms, handling requests in web servers.

### Example:

- **Stack**:
  ```java
  Stack<Integer> stack = new Stack<>();
  stack.push(1);
  stack.push(2);
  int top = stack.pop(); // top = 2
  ```

- **Queue**:
  ```java
  Queue<Integer> queue = new LinkedList<>();
  queue.add(1);
  queue.add(2);
  int front = queue.remove(); // front = 1
  ```

## How to make a queue using 2 stacks?

You can implement a queue using two stacks by leveraging the LIFO nature of stacks to achieve FIFO behavior. The idea is
to use one stack for enqueue operations and the other for dequeue operations.

### Implementation:

```java
class QueueUsingStacks {
    private final Stack<Integer> stack1 = new Stack<>();
    private final Stack<Integer> stack2 = new Stack<>();

    // Enqueue operation
    public void enqueue(int x) {
        stack1.push(x);
    }

    // Dequeue operation
    public int dequeue() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return stack2.pop();
    }
}
```

### Explanation:

1. **Enqueue Operation**: Push the element onto `stack1`.
2. **Dequeue Operation**:
    - If `stack2` is empty, transfer all elements from `stack1` to `stack2` by popping from `stack1` and pushing onto
      `stack2`.
    - Pop the top element from `stack2`.

### Usage:

```java
QueueUsingStacks queue = new QueueUsingStacks();
queue.enqueue(1);
queue.enqueue(2);
int first = queue.dequeue(); // first = 1
int second = queue.dequeue(); // second = 2
```

This implementation ensures that the queue operations maintain the FIFO order using two LIFO stacks.

```
# Serialization in Java

Serialization is a fundamental concept in Java that allows you to convert an object's state into a byte stream, which can then be saved to a file, sent over a network, or stored in a database. This process is essential for various applications, including data persistence, remote method invocation (RMI), and distributed systems.

## What is Serialization?

Serialization is the process of converting an object's state into a byte stream. This byte stream can be reverted back into a copy of the object through deserialization. The primary purpose of serialization is to save the state of an object so that it can be recreated later.

### Key Points:
- **Serialization**: Converting an object into a byte stream.
- **Deserialization**: Converting a byte stream back into an object.
- **Serializable Interface**: A marker interface that classes must implement to be serializable.

## How Serialization Works

### Serializable Interface
To serialize an object, the class must implement the `Serializable` interface. This is a marker interface, meaning it does not contain any methods but signals to the Java Virtual Machine (JVM) that the class can be serialized.

### Example:
```java
import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters
}
```

### ObjectOutputStream and ObjectInputStream

Java provides `ObjectOutputStream` and `ObjectInputStream` classes to handle serialization and deserialization.

- **ObjectOutputStream**: Writes objects to an output stream.
- **ObjectInputStream**: Reads objects from an input stream.

### Serialization Example:

```java
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerializeExample {
    public static void main(String[] args) {
        Student student = new Student(1, "John Doe");

        try (FileOutputStream fileOut = new FileOutputStream("student.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(student);
            System.out.println("Serialized data is saved in student.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
```

### Deserialization Example:

```java
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DeserializeExample {
    public static void main(String[] args) {
        Student student = null;

        try (FileInputStream fileIn = new FileInputStream("student.ser");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            student = (Student) in.readObject();
            System.out.println("Deserialized Student...");
            System.out.println("ID: " + student.getId());
            System.out.println("Name: " + student.getName());
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
    }
}
```

## Advanced Concepts

### SerialVersionUID

The `serialVersionUID` is a unique identifier for each class. It is used during deserialization to verify that the
sender and receiver of a serialized object have loaded classes for that object that are compatible with respect to
serialization.

### Transient Keyword

Fields marked with the `transient` keyword are not serialized. This is useful for fields that do not represent the
object's state or are sensitive and should not be stored.

### Example:

```java
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private transient int age; // This field will not be serialized

    // Constructor, getters, and setters
}
```

### Externalizable Interface

The `Externalizable` interface provides more control over the serialization process. It requires the implementation of
two methods: `writeExternal` and `readExternal`.

### Example:

```java
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Student implements Externalizable {
    private int id;
    private String name;

    public Student() {
        // Default constructor
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(id);
        out.writeObject(name);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        id = in.readInt();
        name = (String) in.readObject();
    }
}
```

## Benefits of Serialization

- **Persistence**: Save the state of an object to a file or database.
- **Communication**: Send objects over a network between different JVMs.
- **Caching**: Store objects in memory for faster access.

## Use Cases

- **Hibernate**: For object-relational mapping (ORM).
- **Remote Method Invocation (RMI)**: To pass objects between JVMs.
- **Java Message Service (JMS)**: For messaging between applications.

Serialization is a powerful feature in Java that enables the easy storage and transmission of objects. Understanding how
to use it effectively can greatly enhance your ability to work with complex data structures and distributed systems.

If you have any more questions or need further clarification, feel free to ask!

```

This detailed explanation should provide a comprehensive understanding of serialization in Java. If you need more specific examples or have additional questions, let me know!
```