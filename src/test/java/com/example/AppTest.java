package com.example;
import org.junit.Test;
import static org.junit.Assert.*;
public class AppTest {
 @Test
 public void testAdd() {
 App app = new App();
 assertEquals(5, app.add(2, 3));
 }
}
