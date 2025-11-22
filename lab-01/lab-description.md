# CMPUT 301 W25 - Lab 1: Java, OOP, and Android Studio! [<img src="https://i.ibb.co/hCdXd3j/1225317672368209920.png" width="40" height="40" style="vertical-align: bottom"/>](https://i.ibb.co/hCdXd3j/1225317672368209920.png)

## 1. Setup Instructions

1. Download and install Android Studio from the [official Android website](https://developer.android.com/studio).

2. Refer to the [installation guide](https://developer.android.com/studio/install) unique to your Operating System.

3. Refer to the lab 1 slides for more information.

## 2. Walkthrough

1. Create a new `LonelyTwitter` project on Android Studio (File > New > New Project > Select "Empty Views Activity").

> [!WARNING]
> Make sure that the project language is **Java**, not Kotlin!

2. Create a new `Tweet` class by navigating to File > New > Java Class
3. Add two attributes to the `Tweet` class:

   - `Date date`
   - `String message`
   - use Alt + Enter (Windows) or Option + Return (Mac) to import any packages

> [!IMPORTANT]
> Access modifiers:
>
> - `private` : class-only access
> - `protected` : package and inheritance access
> - `public` : universal access
> - `No modifier` : package-level access

4. Create two constructors for the `Tweet` class:

   1. Only `message` as argument. Use `Date = new Date()` (current date) for the Default date value.
   2. Both `date` and `message` as arguments.

> [!NOTE]
>
> - All Java classes implicitly extend the `Object` class (java.lang.Object), which provides basic methods like toString(), equals(), and hashCode() that can be overridden.
> - `this` refers to the current instance of the class and is used to distinguish between instance variables and constructor parameters.

5. Make a regular `Tweet` in MainActivity by passing in an empty string:

   ```java
   Tweet tweet = new Tweet("");
   ```

6. Generate getters and setters for the `Tweet` class.
7. Inheritance Implementation

   - Create an `ImportantTweet` child class that extends the `Tweet` class.
   - Include a `super()` call in ImportantTweet's constructors.
   - `ImportantTweet` inherits `Tweet`'s methods and attributes, but requires its own constructors. Try to call `super()` in the child's constructor:

     ```java
     public ImportantTweet(String message) {
         super(message);
         }
     ```

   - `super()` calls the parent's constructor (there is a hidden call to Object's constructor).
   - Change the `Tweet` to an `ImportantTweet` in MainActivity.

8. Abstraction Implementation

- Make Tweet Class Abstract

  ```java
  public abstract class Tweet { ... }
  ```

> [!NOTE]
> Abstract classes cannot be instantiated directly - they can only be used as base classes for inheritance. You must create concrete subclasses to create objects.

- Below is an abstract method, it has no implementation and must be overridden by a child classes to add functionality.

  ```java
  public abstract Boolean isImportant();
  ```

> [!NOTE]
> Abstract methods have no implementation and cannot be called directly. They must be overridden by concrete subclasses before they can be used through objects of those subclasses.

9. Method Overriding

- `ImportantTweet` must override the abstract `isImportant()` method from `Tweet` class.
- The `@Override` annotation ensures correct method overriding at compile-time.
- Each child class can implement `isImportant()` differently based on its needs.

  ```java
  // In ImportantTweet class
  @Override
  public Boolean isImportant() {
    return Boolean.TRUE; // Always returns true for important tweets
  }
  ```

10. Make a `NormalTweet` class, that can have many types of `Tweets`.

- call `super()` in both of NormalTweet's constructors.
- `isImportant()` method should return `Boolean.FALSE`.
- What if we want to use both in our list? (hint - implicit upcasting)

  ```java
  ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
  tweetList.add(normalTweet);
  tweetList.add(importantTweet);

  // Can store both NormalTweet and ImportantTweet objects
  // since they both inherit from Tweet
  for (Tweet tweet : tweetList) {
    System.out.println(tweet.getMessage());
    System.out.println(tweet.isImportant());
  }
  ```

11. Interface Implementation

- Abstract method and base class so all the classes have the `isImportant()` method.
- An interface can also be used to force the use of some methods.

  ```java
    public interface Tweetable {
        public String getMessage();
        public Date getDate();
    }
  ```

- Make `Tweet` class implement `Tweetable` class.
- All classes that implement this interface must provide implementations for these methods.

## 3. Lab Participation Exercise

1. Add three new model classes to `LonelyTwitter`:
   - An abstract base class which represents the current `Mood`.
   - Two non-abstract classes which represent different moods (Ex: happy, sad, etc.) and inherit from the abstract class.
2. Each mood should have a date, and getters and setters to access the date.
3. Provide two constructors:
   - One that sets the date to a default
   - One that takes a date as an argument
4. Follow proper encapsulation principles.
5. Each mood should have a method which returns a string representing that mood.
6. Your new code should demonstrate:
   - Classes
   - Methods
   - Attributes
   - Access modifiers
   - Encapsulation
   - Constructors
   - Inheritance
   - Abstract base classes
7. Update the `README.md` file with your details and references/collaborators.
8. Update the `LICENSE.md` file with your full name.

> [!CAUTION]
> Make sure to commit **and** push your code to the GitHub repository before the deadline!
