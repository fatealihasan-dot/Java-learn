package com.example.data.local

import com.example.data.model.CodingChallenge
import com.example.data.model.LessonEntity
import com.example.data.model.QuizQuestion

object SeedData {
    val initialLessons = listOf(
        LessonEntity(
            id = "java_01_intro",
            moduleId = "mod_basics",
            moduleTitle = "Module 1: Java Basics",
            moduleTitleBn = "মডিউল ১: জাভা বেসিকস",
            title = "Introduction to Java & Hello World",
            titleBn = "জাভা পরিচিতি ও হ্যালো ওয়ার্ল্ড",
            summary = "Understand JVM, JDK, JRE and write your very first Java program.",
            summaryBn = "JVM, JDK, JRE এর ভূমিকা জানুন এবং প্রথম জাভা কোড রান করুন।",
            difficulty = "Beginner",
            estimatedMinutes = 15,
            xpReward = 50,
            codeExample = """public class Main {
    public static void main(String[] args) {
        // জাভায় প্রথম কনসোল প্রিন্ট
        System.out.println("Hello, Java World!");
        System.out.println("স্বাগতম জাভা প্রোগ্রামিং-এ!");
    }
}""",
            expectedOutput = "Hello, Java World!\nস্বাগতম জাভা প্রোগ্রামিং-এ!",
            theoryContentBn = """জাভা একটি প্ল্যাটফর্ম-ইন্ডিপেন্ডেন্ট, অবজেক্ট-ওরিয়েন্টেড প্রোগ্রামিং ল্যাঙ্গুয়েজ।
এর মূল দর্শন হলো: "Write Once, Run Anywhere" (WORA)।

গুরুত্বপূর্ণ অংশসমূহ:
১. JDK (Java Development Kit): কোড লেখা এবং কম্পাইল করার টুলস।
২. JRE (Java Runtime Environment): জাভা প্রোগ্রাম রান করার পরিবেশ।
৩. JVM (Java Virtual Machine): বাইটকোড (.class) কে মেশিন কোডে রূপান্তর করে রান করে।
৪. `public static void main(String[] args)`: প্রতিটি জাভা অ্যাপ্লিকেশনের প্রবেশদ্বার (Entry Point)।""",
            keyPointsJson = "WORA (Write Once Run Anywhere), JVM বাইটকোড এক্সিকিউট করে, main() মেথড থেকে প্রোগ্রাম শুরু হয়",
            sortOrder = 1
        ),
        LessonEntity(
            id = "java_02_datatypes",
            moduleId = "mod_basics",
            moduleTitle = "Module 1: Java Basics",
            moduleTitleBn = "মডিউল ১: জাভা বেসিকস",
            title = "Variables & Data Types",
            titleBn = "ভেরিয়েবল ও ডেটা টাইপ",
            summary = "Learn Primitive types (int, double, boolean) and Reference types (String).",
            summaryBn = "প্রিমিটিভ টাইপ (int, double, boolean) ও রেফারেন্স টাইপ (String) শিখুন।",
            difficulty = "Beginner",
            estimatedMinutes = 20,
            xpReward = 60,
            codeExample = """public class Main {
    public static void main(String[] args) {
        int age = 22;
        double gpa = 3.85;
        char grade = 'A';
        boolean isCoder = true;
        String name = "তানভীর";

        System.out.println("নাম: " + name);
        System.out.println("বয়স: " + age + " বছর");
        System.out.println("সিজিপিএ: " + gpa);
        System.out.println("প্রোগ্রামার: " + isCoder);
    }
}""",
            expectedOutput = "নাম: তানভীর\nবয়স: 22 বছর\nসিজিপিএ: 3.85\nপ্রোগ্রামার: true",
            theoryContentBn = """ভেরিয়েবল হলো মেমোরিতে ডেটা সংরক্ষণের একটি পাত্র। জাভা হলো Statically Typed ল্যাঙ্গুয়েজ, অর্থাৎ ভেরিয়েবলের টাইপ কোডেই নির্ধারণ করতে হয়।

দুই ধরণের ডেটা টাইপ:
১. Primitive Types (৮টি): byte, short, int, long, float, double, char, boolean।
২. Non-Primitive / Reference Types: String, Arrays, Classes, Interfaces।""",
            keyPointsJson = "জাভা Statically-typed ল্যাঙ্গুয়েজ, প্রিমিটিভ টাইপে সরাসরি মান জমা থাকে, স্ট্রিং হলো রেফারেন্স অবজেক্ট",
            sortOrder = 2
        ),
        LessonEntity(
            id = "java_03_operators",
            moduleId = "mod_basics",
            moduleTitle = "Module 1: Java Basics",
            moduleTitleBn = "মডিউল ১: জাভা বেসিকস",
            title = "Operators & Expressions",
            titleBn = "অপারেটর ও এক্সপ্রেশন",
            summary = "Arithmetic (+, -, *, /), Relational (==, !=, >), and Logical (&&, ||, !).",
            summaryBn = "গাণিতিক, রিলেশনাল ও লজিক্যাল অপারেটরের ব্যবহার শিখুন।",
            difficulty = "Beginner",
            estimatedMinutes = 15,
            xpReward = 50,
            codeExample = """public class Main {
    public static void main(String[] args) {
        int a = 15;
        int b = 4;

        System.out.println("যোগফল: " + (a + b));
        System.out.println("ভাগশেষ (Modulus): " + (a % b));
        
        boolean canVote = (a >= 18);
        System.out.println("ভোট দেওয়ার যোগ্য: " + canVote);
    }
}""",
            expectedOutput = "যোগফল: 19\nভাগশেষ (Modulus): 3\nভোট দেওয়ার যোগ্য: false",
            theoryContentBn = """অপারেটর হলো বিশেষ চিহ্ন যা অপারেন্ডগুলোর উপর নির্দিষ্ট অপারেশন চালায়।
- Arithmetic: +, -, *, /, %
- Relational: ==, !=, >, <, >=, <=
- Logical: && (AND), || (OR), ! (NOT)
- Assignment: =, +=, -=, *=, /=""",
            keyPointsJson = "% দিয়ে ভাগশেষ বের করা হয়, && শর্তে উভয় সত্য হতে হয়, || শর্তে যেকোনো একটি সত্য হলেই হয়",
            sortOrder = 3
        ),
        LessonEntity(
            id = "java_04_control_flow",
            moduleId = "mod_control",
            moduleTitle = "Module 2: Control Flow",
            moduleTitleBn = "মডিউল ২: কন্ট্রোল ফ্লো",
            title = "Conditional Logic (if-else & switch)",
            titleBn = "কন্ডিশনাল লজিক (if-else ও switch)",
            summary = "Make decisions in code using if, else if, else and switch-case statements.",
            summaryBn = "if-else এবং switch-case স্টেটমেন্টের মাধ্যমে কোডে সিদ্ধান্ত গ্রহণ করুন।",
            difficulty = "Beginner",
            estimatedMinutes = 20,
            xpReward = 70,
            codeExample = """public class Main {
    public static void main(String[] args) {
        int score = 85;

        if (score >= 80) {
            System.out.println("গ্রেড: A+ (চমৎকার!)");
        } else if (score >= 70) {
            System.out.println("গ্রেড: A");
        } else {
            System.out.println("আরও উন্নতি করতে হবে!");
        }
        
        int day = 3;
        switch (day) {
            case 1: System.out.println("শনিবার"); break;
            case 2: System.out.println("রবিবার"); break;
            case 3: System.out.println("সোমবার"); break;
            default: System.out.println("অন্যান্য দিন");
        }
    }
}""",
            expectedOutput = "গ্রেড: A+ (চমৎকার!)\nসোমবার",
            theoryContentBn = """কন্ডিশনাল স্টেটমেন্ট দিয়ে প্রোগ্রামের প্রবাহ নিয়ন্ত্রণ করা যায়।
`if` ব্লকের শর্ত true হলে তা কার্যকর হয়। একাধিক অপশনের ক্ষেত্রে `switch-case` পরিষ্কার ও দ্রুতগতির সমাধান দেয়। `break` স্টেটমেন্ট ফল-থ্রু (fall-through) আটকায়।""",
            keyPointsJson = "if-else কন্ডিশনাল ব্রাঞ্চিং তৈরি করে, switch-case এ break না দিলে পরবর্তী কেসগুলোও রান হয়ে যায়",
            sortOrder = 4
        ),
        LessonEntity(
            id = "java_05_loops",
            moduleId = "mod_control",
            moduleTitle = "Module 2: Control Flow",
            moduleTitleBn = "মডিউল ২: কন্ট্রোল ফ্লো",
            title = "Loops (for, while, do-while)",
            titleBn = "লুপস (for, while, do-while)",
            summary = "Master iteration with traditional for, enhanced for-each, and while loops.",
            summaryBn = "for লুপ, enhanced for-each এবং while লুপের মাধ্যমে পুনরাবৃত্তি শিখুন।",
            difficulty = "Beginner",
            estimatedMinutes = 25,
            xpReward = 80,
            codeExample = """public class Main {
    public static void main(String[] args) {
        // ১ থেকে ৫ পর্যন্ত যোগফল বের করা
        int sum = 0;
        for (int i = 1; i <= 5; i++) {
            sum += i;
        }
        System.out.println("১ থেকে ৫ এর যোগফল: " + sum);

        // While লুপ উদাহরণ
        int count = 3;
        while (count > 0) {
            System.out.println("কাউন্টডাউন: " + count);
            count--;
        }
        System.out.println("লঞ্চ!");
    }
}""",
            expectedOutput = "১ থেকে ৫ এর যোগফল: 15\nকাউন্টডাউন: 3\nকাউন্টডাউন: 2\nকাউন্টডাউন: 1\nলঞ্চ!",
            theoryContentBn = """লুপ একই কোড বারবার চালানোর উপায়।
১. `for` loop: যখন পুনরাবৃত্তির সংখ্যা আগে থেকেই জানা থাকে।
২. `while` loop: যতক্ষণ শর্ত সত্য থাকে ততক্ষণ চলে।
৩. `do-while` loop: অন্তত একবার কোড রান হয়, তারপর শর্ত পরীক্ষা করে।
৪. `break` ও `continue`: লুপ সম্পূর্ণ বন্ধ বা বর্তমান ধাপ স্কিপ করার জন্য।""",
            keyPointsJson = "for লুপ নির্দিষ্ট সংখ্যক পুনরাবৃত্তির জন্য শ্রেষ্ঠ, do-while কমপক্ষে একবার রান হবেই",
            sortOrder = 5
        ),
        LessonEntity(
            id = "java_06_methods",
            moduleId = "mod_control",
            moduleTitle = "Module 2: Control Flow",
            moduleTitleBn = "মডিউল ২: কন্ট্রোল ফ্লো",
            title = "Methods & Overloading",
            titleBn = "মেথডস ও মেথড ওভারলোডিং",
            summary = "Write reusable blocks of code with parameters, return types and overloading.",
            summaryBn = "প্যারামিটার, রিটার্ন টাইপ ও মেথড ওভারলোডিং সহ পুনঃব্যবহারযোগ্য কোড ব্লক।",
            difficulty = "Beginner",
            estimatedMinutes = 20,
            xpReward = 75,
            codeExample = """public class Main {
    // দুটি পূর্ণসংখ্যার যোগফল নির্ণয় মেথড
    public static int add(int x, int y) {
        return x + y;
    }

    // মেথড ওভারলোডিং (একই নাম, ভিন্ন প্যারামিটার)
    public static double add(double x, double y) {
        return x + y;
    }

    public static void main(String[] args) {
        int result1 = add(10, 25);
        double result2 = add(5.5, 4.3);
        
        System.out.println("পূর্ণসংখ্যার যোগফল: " + result1);
        System.out.println("দশমিকের যোগফল: " + result2);
    }
}""",
            expectedOutput = "পূর্ণসংখ্যার যোগফল: 35\nদশমিকের যোগফল: 9.8",
            theoryContentBn = """মেথড হলো কোডের একটি গুচ্ছ যা নির্দিষ্ট কাজ সম্পন্ন করে। কোড রিইউজেবিলিটি বাড়ানোর মূল উপায় হলো মেথড।
মেথড ওভারলোডিং (Method Overloading) হলো একই নামে একাধিক মেথড তৈরি করা, যার প্যারামিটারের সংখ্যা বা টাইপ ভিন্ন।""",
            keyPointsJson = "DRY (Don't Repeat Yourself) নীতি বাস্তবায়ন করে মেথড, ওভারলোডিংয়ে প্যারামিটার লিস্ট ভিন্ন হতে হয়",
            sortOrder = 6
        ),
        LessonEntity(
            id = "java_07_oop_basics",
            moduleId = "mod_oop",
            moduleTitle = "Module 3: Object-Oriented Programming (OOP)",
            moduleTitleBn = "মডিউল ৩: অবজেক্ট-ওরিয়েন্টেড প্রোগ্রামিং (OOP)",
            title = "Classes, Objects & Constructors",
            titleBn = "ক্লাস, অবজেক্ট ও কনস্ট্রাক্টর",
            summary = "The blueprint of Java: creating classes, instantiating objects, and constructors.",
            summaryBn = "জাভার মূল ভিত্তি: ক্লাস তৈরি, অবজেক্ট ইনস্ট্যানশিয়েট করা এবং কনস্ট্রাক্টর।",
            difficulty = "Intermediate",
            estimatedMinutes = 30,
            xpReward = 100,
            codeExample = """class Student {
    String name;
    int roll;

    // কনস্ট্রাক্টর
    public Student(String name, int roll) {
        this.name = name;
        this.roll = roll;
    }

    public void displayInfo() {
        System.out.println("শিক্ষার্থী: " + name + ", রোল: " + roll);
    }
}

public class Main {
    public static void main(String[] args) {
        Student s1 = new Student("রাকিব", 101);
        Student s2 = new Student("সাদিয়া", 102);

        s1.displayInfo();
        s2.displayInfo();
    }
}""",
            expectedOutput = "শিক্ষার্থী: রাকিব, রোল: 101\nশিক্ষার্থী: সাদিয়া, রোল: 102",
            theoryContentBn = """OOP-এর ভিত্তি হলো ক্লাস ও অবজেক্ট।
১. Class: এটি অবজেক্ট তৈরির ব্লুপ্রিন্ট বা নকশা।
২. Object: ক্লাসের একটি বাস্তব রূপ (Instance)।
৩. Constructor: অবজেক্ট তৈরির সময় স্বয়ংক্রিয়ভাবে কল হওয়া বিশেষ মেথড, যার নাম ক্লাসের নামের সমান এবং কোনো রিটার্ন টাইপ থাকে না।
৪. `this` keyword: বর্তমান অবজেক্টের ইনস্ট্যান্স ভেরিয়েবল নির্দেশ করে।""",
            keyPointsJson = "ক্লাস হলো ব্লুপ্রিন্ট, অবজেক্ট হলো ক্লাসের বাস্তব রূপ, new কিওয়ার্ড দিয়ে মেমোরি এলোকেট হয়",
            sortOrder = 7
        ),
        LessonEntity(
            id = "java_08_oop_inheritance",
            moduleId = "mod_oop",
            moduleTitle = "Module 3: Object-Oriented Programming (OOP)",
            moduleTitleBn = "মডিউল ৩: অবজেক্ট-ওরিয়েন্টেড প্রোগ্রামিং (OOP)",
            title = "Inheritance & Method Overriding",
            titleBn = "ইনহেরিটেন্স ও মেথড ওভাররাইডিং",
            summary = "Reusing code across classes using 'extends' and 'super'.",
            summaryBn = "extends এবং super কিওয়ার্ড ব্যবহার করে প্যারেন্ট-চাইল্ড ক্লাসের সম্পর্ক তৈরি।",
            difficulty = "Intermediate",
            estimatedMinutes = 30,
            xpReward = 100,
            codeExample = """class Animal {
    void makeSound() {
        System.out.println("প্রাণী শব্দ করে");
    }
}

class Dog extends Animal {
    @Override
    void makeSound() {
        System.out.println("কুকুর ঘেউ ঘেউ করে 🐶");
    }
}

public class Main {
    public static void main(String[] args) {
        Animal myDog = new Dog();
        myDog.makeSound();
    }
}""",
            expectedOutput = "কুকুর ঘেউ ঘেউ করে 🐶",
            theoryContentBn = """ইনহেরিটেন্সের মাধ্যমে একটি ক্লাস (Child / Subclass) অন্য ক্লাসের (Parent / Superclass) বৈশিষ্ট্য ও মেথডগুলো নিজের মধ্যে ধারণ করে।
জাভায় `extends` দিয়ে ইনহেরিট করা হয়।
`@Override` অ্যানোটেশন দিয়ে প্যারেন্ট ক্লাসের মেথডকে চাইল্ড ক্লাসে নিজের মতো পরিবর্তন করা হয়।
জাভায় ক্লাসের ক্ষেত্রে মাল্টিপল ইনহেরিটেন্স সরাসরি সাপোর্ট করে না (ডায়মন্ড প্রবলেম এড়াতে)।""",
            keyPointsJson = "extends দিয়ে ইনহেরিট হয়, super দিয়ে প্যারেন্ট ক্লাসকে এক্সেস করা যায়, মেথড ওভাররাইডিং রানটাইম পলিমরফিজম",
            sortOrder = 8
        ),
        LessonEntity(
            id = "java_09_oop_encapsulation",
            moduleId = "mod_oop",
            moduleTitle = "Module 3: Object-Oriented Programming (OOP)",
            moduleTitleBn = "মডিউল ৩: অবজেক্ট-ওরিয়েন্টেড প্রোগ্রামিং (OOP)",
            title = "Encapsulation & Access Modifiers",
            titleBn = "এনক্যাপসুলেশন ও এক্সেস মডিফায়ার",
            summary = "Hiding internal data using private fields and providing public getters/setters.",
            summaryBn = "ডেটা হাইডিং, private ভেরিয়েবল এবং public getter ও setter মেথডের ব্যবহার।",
            difficulty = "Intermediate",
            estimatedMinutes = 25,
            xpReward = 90,
            codeExample = """class BankAccount {
    private double balance; // প্রাইভেট ডেটা

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("জমা হয়েছে: ৳" + amount);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount(5000.0);
        acc.deposit(2500.0);
        System.out.println("বর্তমান ব্যালেন্স: ৳" + acc.getBalance());
    }
}""",
            expectedOutput = "জমা হয়েছে: ৳2500.0\nবর্তমান ব্যালেন্স: ৳7500.0",
            theoryContentBn = """এনক্যাপসুলেশন হলো ডেটা এবং সেই ডেটা নিয়ে কাজ করা মেথডগুলোকে একটি ক্যাপসুলের মধ্যে গুটিয়ে রাখা।
এর সুবিধা হলো ডেটা সিকিউরিটি বা ডেটা হাইডিং। সরাসরি ভেরিয়েবল পরিবর্তন করতে না দিয়ে গেটার (Getter) ও সেটার (Setter) মেথডের মাধ্যমে শর্তযুক্ত পরিবর্তন নিশ্চিত করা হয়।""",
            keyPointsJson = "private ফিল্ড বাইরের এক্সেস বন্ধ করে, getter/setter দিয়ে নিরাপদ ডেটা লেনদেন হয়",
            sortOrder = 9
        ),
        LessonEntity(
            id = "java_10_oop_abstraction",
            moduleId = "mod_oop",
            moduleTitle = "Module 3: Object-Oriented Programming (OOP)",
            moduleTitleBn = "মডিউল ৩: অবজেক্ট-ওরিয়েন্টেড প্রোগ্রামিং (OOP)",
            title = "Abstraction & Interfaces",
            titleBn = "অ্যাবস্ট্রাকশন ও ইন্টারফেস",
            summary = "Define abstract contracts using 'abstract' and 'interface' for loose coupling.",
            summaryBn = "abstract ক্লাস এবং interface ব্যবহার করে বাস্তবায়নের বিশদ লুকিয়ে কন্ট্রাক্ট তৈরি।",
            difficulty = "Intermediate",
            estimatedMinutes = 30,
            xpReward = 110,
            codeExample = """interface PaymentGateway {
    void processPayment(double amount);
}

class BkashPayment implements PaymentGateway {
    @Override
    public void processPayment(double amount) {
        System.out.println("বিকাশে ৳" + amount + " সফলভাবে পেমেন্ট হয়েছে!");
    }
}

public class Main {
    public static void main(String[] args) {
        PaymentGateway gateway = new BkashPayment();
        gateway.processPayment(1200.0);
    }
}""",
            expectedOutput = "বিকাশে ৳1200.0 সফলভাবে পেমেন্ট হয়েছে!",
            theoryContentBn = """অ্যাবস্ট্রাকশন হলো অপ্রয়োজনীয় জটিলতা আড়াল করে শুধুমাত্র আবশ্যক বৈশিষ্ট্যগুলো প্রকাশ করা।
১. Abstract Class: কিছু বাস্তবায়িত মেথড ও কিছু অ্যাবস্ট্রাক্ট মেথড থাকতে পারে।
২. Interface: শতভাগ অ্যাবস্ট্রাক্ট ব্লুপ্রিন্ট (Java 8+ এ default ও static মেথড থাকতে পারে)। একটি ক্লাস একাধিক ইন্টারফেস `implements` করতে পারে।""",
            keyPointsJson = "ইন্টারফেস দিয়ে মাল্টিপল ইনহেরিটেন্সের সুবিধা পাওয়া যায়, loose-coupling ডিজাইন নিশ্চিত হয়",
            sortOrder = 10
        ),
        LessonEntity(
            id = "java_11_collections_arraylist",
            moduleId = "mod_collections",
            moduleTitle = "Module 4: Collections & Data Structures",
            moduleTitleBn = "মডিউল ৪: কালেকশন ও ডেটা স্ট্রাকচার",
            title = "ArrayList & Dynamic Lists",
            titleBn = "অ্যারে-লিস্ট ও ডাইনামিক ডেটা স্ট্রাকচার",
            summary = "Store resizable collections of objects using java.util.ArrayList.",
            summaryBn = "ডাইনামিক আকারের উপাদান সংরক্ষণের জন্য java.util.ArrayList ব্যবহার।",
            difficulty = "Intermediate",
            estimatedMinutes = 25,
            xpReward = 85,
            codeExample = """import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> languages = new ArrayList<>();
        languages.add("Java");
        languages.add("Kotlin");
        languages.add("Python");

        System.out.println("মোট ভাষা সংখ্যা: " + languages.size());
        
        for (String lang : languages) {
            System.out.println("শেখানো হচ্ছে: " + lang);
        }
    }
}""",
            expectedOutput = "মোট ভাষা সংখ্যা: 3\nশেখানো হচ্ছে: Java\nশেখানো হচ্ছে: Kotlin\nশেখানো হচ্ছে: Python",
            theoryContentBn = """সাধারণ অ্যারে (Array) এর সাইজ ফিক্সড থাকে, কিন্তু ArrayList স্বয়ংক্রিয়ভাবে তার সাইজ বাড়াতে বা কমাতে পারে।
উপকারী মেথডসমূহ:
- `.add(item)`: উপাদান যোগ করা
- `.get(index)`: ইনডেক্স থেকে উপাদান পাওয়া
- `.remove(index)`: উপাদান মুছে ফেলা
- `.size()`: মোট উপাদান সংখ্যা জানা""",
            keyPointsJson = "ArrayList ডাইনামিক অ্যারে, এর সাইজ পরিবর্তনশীল, এটি জেনেরিকস <Type> সাপোর্ট করে",
            sortOrder = 11
        ),
        LessonEntity(
            id = "java_12_collections_hashmap",
            moduleId = "mod_collections",
            moduleTitle = "Module 4: Collections & Data Structures",
            moduleTitleBn = "মডিউল ৪: কালেকশন ও ডেটা স্ট্রাকচার",
            title = "HashMap (Key-Value Pairs)",
            titleBn = "হ্যাশম্যাপ (কি-ভ্যালু জোড়া)",
            summary = "Fast O(1) lookups using keys and values in java.util.HashMap.",
            summaryBn = "কী (Key) এবং ভ্যালু (Value) দিয়ে দ্রুত ডেটা খোঁজার উপায়।",
            difficulty = "Intermediate",
            estimatedMinutes = 25,
            xpReward = 90,
            codeExample = """import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> studentMarks = new HashMap<>();
        studentMarks.put("সাকিব", 88);
        studentMarks.put("আরিফ", 94);
        studentMarks.put("মিম", 91);

        System.out.println("আরিফের নম্বর: " + studentMarks.get("আরিফ"));
        System.out.println("সাকিব তালিকায় আছে কি? " + studentMarks.containsKey("সাকিব"));
    }
}""",
            expectedOutput = "আরিফের নম্বর: 94\nসাকিব তালিকায় আছে কি? true",
            theoryContentBn = """HashMap হলো কী-ভ্যালু (Key-Value) জোড়ায় ডেটা সংরক্ষণের অন্যতম জনপ্রিয় ডেটা স্ট্রাকচার।
প্রতিটি Key ইউনিক হতে হয়। হ্যাশিং টেকনিক ব্যবহার করার কারণে গড় সময়ে O(1) টাইম কমপ্লেক্সিটিতে উপাদান সার্চ, ইনসার্ট ও ডিলিট করা সম্ভব হয়।""",
            keyPointsJson = "HashMap কী-ভ্যালু স্টোর করে, Key সবসময় ইউনিক হতে হয়, সার্চ কমপ্লেক্সিটি ওয়ান O(1)",
            sortOrder = 12
        ),
        LessonEntity(
            id = "java_13_exceptions",
            moduleId = "mod_advanced",
            moduleTitle = "Module 5: Exception Handling & Robustness",
            moduleTitleBn = "মডিউল ৫: এক্সেপশন হ্যান্ডলিং ও রোবাস্ট কোডিং",
            title = "Try-Catch-Finally Exception Handling",
            titleBn = "Try-Catch ও এক্সেপশন হ্যান্ডলিং",
            summary = "Prevent app crashes and handle runtime errors gracefully.",
            summaryBn = "প্রোগ্রাম ক্র্যাশ এড়াতে এবং রানটাইম ত্রুটি সঠিকভাবে সামলাতে try-catch ব্লক।",
            difficulty = "Advanced",
            estimatedMinutes = 25,
            xpReward = 95,
            codeExample = """public class Main {
    public static void main(String[] args) {
        try {
            int a = 10;
            int b = 0;
            int result = a / b; // শুন্য দিয়ে ভাগ করার চেষ্টা
            System.out.println("ফলাফল: " + result);
        } catch (ArithmeticException e) {
            System.out.println("ত্রুটি ধরা পড়েছে: শূন্য দিয়ে ভাগ করা সম্ভব নয়!");
        } finally {
            System.out.println("Finally ব্লক সর্বদা নির্বাহ হয়। ক্লিনআপ সমাপ্ত।");
        }
    }
}""",
            expectedOutput = "ত্রুটি ধরা পড়েছে: শূন্য দিয়ে ভাগ করা সম্ভব নয়!\nFinally ব্লক সর্বদা নির্বাহ হয়। ক্লিনআপ সমাপ্ত।",
            theoryContentBn = """এক্সেপশন হলো প্রোগ্রামের রানটাইমে ঘটা অপ্রত্যাশিত ত্রুটি যা স্বাভাবিক প্রবাহ ব্যাহত করে।
- `try`: যে কোডে ত্রুটি ঘটার আশঙ্কা থাকে তা এখানে থাকে।
- `catch`: নির্দিষ্ট এক্সেপশন হ্যান্ডেল করে।
- `finally`: ত্রুটি হোক বা না হোক, এই ব্লক নিশ্চিতভাবে রান হয় (যেমন ডাটাবেস বা ফাইল ক্লোজ করতে)।
- `throw` / `throws`: কাস্টম এক্সেপশন ছুড়তে বা মেথড সিগনেচারে ঘোষণা দিতে ব্যবহৃত হয়।""",
            keyPointsJson = "try-catch অ্যাপ ক্র্যাশ হওয়া থেকে বাঁচায়, finally ব্লক সর্বদা এক্সিকিউট হয়",
            sortOrder = 13
        ),
        LessonEntity(
            id = "java_14_threads_lambdas",
            moduleId = "mod_advanced",
            moduleTitle = "Module 5: Exception Handling & Robustness",
            moduleTitleBn = "মডিউল ৫: এক্সেপশন হ্যান্ডলিং ও রোবাস্ট কোডিং",
            title = "Lambda Expressions & Multithreading",
            titleBn = "ল্যাম্বডা এক্সপ্রেশন ও মাল্টিথ্রেডিং পরিচিতি",
            summary = "Modern functional programming with Java 8 lambdas and parallel threads.",
            summaryBn = "জাভা ৮ এর আধুনিক ল্যাম্বডা সিনট্যাক্স এবং ব্যাকগ্রাউন্ড থ্রেড তৈরি।",
            difficulty = "Advanced",
            estimatedMinutes = 30,
            xpReward = 120,
            codeExample = """public class Main {
    public static void main(String[] args) {
        // ল্যাম্বডা এক্সপ্রেশন দিয়ে Runnable থ্রেড
        Runnable task = () -> {
            System.out.println("ব্যাকগ্রাউন্ড থ্রেডে কাজ চলছে: " + Thread.currentThread().getName());
        };

        Thread thread = new Thread(task);
        thread.start();

        System.out.println("মেইন থ্রেড সমাপ্ত: " + Thread.currentThread().getName());
    }
}""",
            expectedOutput = "মেইন থ্রেড সমাপ্ত: main\nব্যাকগ্রাউন্ড থ্রেডে কাজ চলছে: Thread-0",
            theoryContentBn = """ল্যাম্বডা এক্সপ্রেশন (Lambda Expression) হলো অ্যানোনিমাস ফাংশন যা কোডকে সংক্ষিপ্ত ও আধুনিক করে তোলে (যেমন: `(param) -> { body }`)।
মাল্টিথ্রেডিং (Multithreading) একই সাথে একাধিক কাজ প্যারালালে সম্পন্ন করতে ব্যবহৃত হয়, যা বিশেষ করে অ্যান্ড্রয়েড ব্যাকগ্রাউন্ড প্রসেসিংয়ের মূল ভিত্তি।""",
            keyPointsJson = "ল্যাম্বডা কোডকে ক্লিন করে, থ্রেড প্যারালাল এক্সিকিউশন নিশ্চিত করে",
            sortOrder = 14
        )
    )

    val sampleQuizzes = listOf(
        QuizQuestion(
            id = "q1",
            lessonId = "java_01_intro",
            question = "What component in Java is responsible for executing the bytecode?",
            questionBn = "জাভায় বাইটকোড এক্সিকিউট করার দায়িত্ব কার?",
            options = listOf("JDK (Java Development Kit)", "JVM (Java Virtual Machine)", "JRE", "Javac Compiler"),
            correctIndex = 1,
            explanationBn = "সঠিক উত্তর JVM! Javac কম্পাইলার কোডকে বাইটকোডে রূপান্তর করে এবং JVM তা নির্দিষ্ট অপারেটিং সিস্টেমে এক্সিকিউট করে।"
        ),
        QuizQuestion(
            id = "q2",
            lessonId = "java_02_datatypes",
            question = "Which of the following is NOT a primitive data type in Java?",
            questionBn = "নিচের কোনটি জাভায় প্রিমিটিভ (Primitive) ডেটা টাইপ নয়?",
            options = listOf("int", "boolean", "String", "char"),
            correctIndex = 2,
            explanationBn = "String হলো একটি ক্লাস বা নন-প্রিমিটিভ রেফারেন্স অবজেক্ট, বাকিগুলো প্রিমিটিভ ডেটা টাইপ।"
        ),
        QuizQuestion(
            id = "q3",
            lessonId = "java_04_control_flow",
            question = "What happens if a switch case doesn't have a 'break' statement?",
            questionBn = "switch কেসের শেষে 'break' না দিলে কী ঘটে?",
            options = listOf("কম্পাইল এরর হবে", "প্রোগ্রাম ক্র্যাশ করবে", "পরের কেসগুলোও স্বয়ংক্রিয়ভাবে এক্সিকিউট হয়ে যাবে (Fall-through)", "লুপ শুরু হবে"),
            correctIndex = 2,
            explanationBn = "break না দিলে জাভা নিচের সব কেস এক্সিকিউট করে ফেলবে যতক্ষণ না কোনো break পাওয়া যায় বা switch শেষ হয়।"
        ),
        QuizQuestion(
            id = "q4",
            lessonId = "java_07_oop_basics",
            question = "Which keyword is used to allocate memory and create an object in Java?",
            questionBn = "জাভায় অবজেক্ট তৈরি এবং মেমোরি এলোকেট করতে কোন কিওয়ার্ড ব্যবহৃত হয়?",
            options = listOf("this", "new", "class", "instance"),
            correctIndex = 1,
            explanationBn = "new কিওয়ার্ড ক্লাসের ইনস্ট্যান্স বা অবজেক্ট তৈরি করতে মেমোরিতে জায়গা তৈরি করে এবং কনস্ট্রাক্টর কল করে।"
        ),
        QuizQuestion(
            id = "q5",
            lessonId = "java_08_oop_inheritance",
            question = "Which keyword is used by a child class to inherit a parent class?",
            questionBn = "প্যারেন্ট ক্লাস থেকে ইনহেরিট করতে চাইল্ড ক্লাসে কোন কিওয়ার্ড ব্যবহৃত হয়?",
            options = listOf("implements", "inherits", "extends", "super"),
            correctIndex = 2,
            explanationBn = "জাভায় ক্লাস ইনহেরিট করতে `extends` কিওয়ার্ড এবং ইন্টারফেসের ক্ষেত্রে `implements` ব্যবহৃত হয়।"
        ),
        QuizQuestion(
            id = "q6",
            lessonId = "java_09_oop_encapsulation",
            question = "What is the primary purpose of Encapsulation in OOP?",
            questionBn = "OOP-তে এনক্যাপসুলেশনের প্রধান উদ্দেশ্য কী?",
            options = listOf("কোডের গতি দ্বিগুণ করা", "ডেটা হাইডিং ও নিরাপত্তা নিশ্চিত করা", "মেমোরি সেভ করা", "মাল্টিপল ইনহেরিটেন্স আনা"),
            correctIndex = 1,
            explanationBn = "এনক্যাপসুলেশন ভেরিয়েবলগুলোকে private করে এবং getter/setter এর মাধ্যমে নিয়ন্ত্রণ বজায় রেখে তথ্যের নিরাপত্তা নিশ্চিত করে।"
        ),
        QuizQuestion(
            id = "q7",
            lessonId = "java_11_collections_arraylist",
            question = "How is an ArrayList fundamentally different from a regular Java array?",
            questionBn = "একটি সাধারণ অ্যারের সাথে ArrayList-এর প্রধান পার্থক্য কী?",
            options = listOf("ArrayList এ প্রিমিটিভ রাখা যায় না", "ArrayList এর সাইজ পরিবর্তনশীল (Dynamic)", "ArrayList দ্রুতগতির", "উভয়ই একই"),
            correctIndex = 1,
            explanationBn = "অ্যারের ফিক্সড সাইজ থাকে, কিন্তু ArrayList স্বয়ংক্রিয়ভাবে প্রয়োজন অনুযায়ী সাইজ বড় বা ছোট করতে পারে।"
        ),
        QuizQuestion(
            id = "q8",
            lessonId = "java_13_exceptions",
            question = "Which block always executes in an exception handling construct?",
            questionBn = "এক্সেপশন ঘটুক বা না ঘটুক, কোন ব্লকটি সর্বদা রান হয়?",
            options = listOf("try", "catch", "finally", "throw"),
            correctIndex = 2,
            explanationBn = "finally ব্লক সর্বদা নির্বাহ হয়, তাই রিসোর্স বা কানেকশন ক্লোজ করার কাজে এটি অত্যন্ত জরুরি।"
        )
    )

    val sampleChallenges = listOf(
        CodingChallenge(
            id = "ch_01",
            title = "Even or Odd Checker",
            titleBn = "জোড় বা বিজোড় সংখ্যা নির্ণয়",
            descriptionBn = "একটি সংখ্যা যদি ২ দ্বারা নিঃশেষে বিভাজ্য হয় তবে 'Even' প্রিন্ট করুন, নতুবা 'Odd' প্রিন্ট করুন।",
            starterCode = """public class Solution {
    public static void checkEvenOdd(int num) {
        if (num % 2 == 0) {
            System.out.println("Even");
        } else {
            System.out.println("Odd");
        }
    }
    public static void main(String[] args) {
        checkEvenOdd(14);
    }
}""",
            testInput = "14",
            expectedOutput = "Even",
            hintBn = "মডুলাস অপারেটর (%) ব্যবহার করে ভাগশেষ ০ কি না যাচাই করুন।",
            difficulty = "Easy",
            xpReward = 40
        ),
        CodingChallenge(
            id = "ch_02",
            title = "Factorial Calculator",
            titleBn = "ফ্যাক্টোরিয়াল ক্যালকুলেটর",
            descriptionBn = "প্রদত্ত সংখ্যা N এর ফ্যাক্টোরিয়াল বের করুন (যেমন 5! = 5 * 4 * 3 * 2 * 1 = 120)।",
            starterCode = """public class Solution {
    public static int factorial(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
    public static void main(String[] args) {
        System.out.println(factorial(5));
    }
}""",
            testInput = "5",
            expectedOutput = "120",
            hintBn = "একটি for লুপ ব্যবহার করে ১ থেকে N পর্যন্ত গুণ করুন।",
            difficulty = "Medium",
            xpReward = 60
        ),
        CodingChallenge(
            id = "ch_03",
            title = "Reverse a String",
            titleBn = "স্ট্রিং উল্টানো (Reverse String)",
            descriptionBn = "যেকোনো একটি স্ট্রিংকে উল্টে আউটপুট দিন (যেমন: 'JAVA' -> 'AVAJ')।",
            starterCode = """public class Solution {
    public static String reverse(String str) {
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }
    public static void main(String[] args) {
        System.out.println(reverse("JAVA"));
    }
}""",
            testInput = "JAVA",
            expectedOutput = "AVAJ",
            hintBn = "StringBuilder এর reverse() মেথড অথবা উল্টো দিক থেকে লুপ চালিয়ে ক্যারেক্টার যুক্ত করুন।",
            difficulty = "Medium",
            xpReward = 75
        )
    )
}
