package org.example.story;

import static org.junit.jupiter.api.Assertions.*;

import org.example.story.characters.Ground;
import org.example.story.characters.Person;
import org.example.story.characters.Wind;
import org.example.story.enums.DamagesPart;
import org.example.story.enums.States;
import org.example.story.nature.Flora;
import org.example.story.nature.FloraItems;
import org.example.story.nature.Swamp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class StoryTest {

    private PrintStream originalOut;

    private ByteArrayOutputStream getOutputStream() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }

    @AfterEach
    public void soutRestore() {
        if (originalOut != null)
            System.setOut(originalOut);
    }

    @ParameterizedTest
    @CsvSource({
            "30, 20, 80, горло",
            "100, 75, 20, уши",
            "45, 46, 46, глаза",
            "50, 50, 50, уши"
    })
    public void findMostDamagedPartTest(int d1, int d2, int d3, String part) {
        Person person = new Person("p1", States.NORMAL);
        Wind wind = new Wind();
        wind.negativeAction(person, DamagesPart.EARS, d1);
        wind.negativeAction(person, DamagesPart.EYES, d2);
        wind.negativeAction(person, DamagesPart.THROAT, d3);
        DamagesPart damagesPart = person.findMostDamagedPart();

        assertEquals(part, damagesPart.getDescription());
    }

    @ParameterizedTest
    @CsvSource({
            "100, 100, 100",
            "0, 0, 0"
    })
    public void findMostDamagedPartTestAllEquals(int d1, int d2, int d3) {
        Person person = new Person("p1", States.NORMAL);
        Wind wind = new Wind();
        wind.negativeAction(person, DamagesPart.EARS, d1);
        wind.negativeAction(person, DamagesPart.EYES, d2);
        wind.negativeAction(person, DamagesPart.THROAT, d3);

        ByteArrayOutputStream outputStream = getOutputStream();
        person.findMostDamagedPart();
        assertEquals("У персонажа p1 всё одинаково повреждено.\r\n", outputStream.toString());
    }

    @Test
    public void findMostDamagedPartTestEmpty() {
        Person person = new Person("p1", States.NORMAL);
        ByteArrayOutputStream outputStream = getOutputStream();
        DamagesPart damagesPart = person.findMostDamagedPart();
        assertEquals("У персонажа p1 нет повреждений.\r\n", outputStream.toString());
        assertNull(damagesPart);
    }

    @Test
    public void describeFloraOfSwampNoFlora() {
        Swamp swamp = new Swamp("Сибирское");
        ByteArrayOutputStream outputStream = getOutputStream();
        swamp.describeFlora();
        assertEquals("""
                Болото Сибирское лишено растительности.\r
                Болото Сибирское покрыто пылью в 0.0 дюйм толщиной.\r
                """, outputStream.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "GRASS, TREES, 2",
            "FLOWERS, GRASS, 10",
            "TREES, FLOWERS, 3.5"
    })
    public void describeFloraOfSwampWithFlora(String f1, String f2, double dust) {
        Swamp swamp = new Swamp("Сибирское");
        FloraItems flora1 = FloraItems.valueOf(f1);
        FloraItems flora2 = FloraItems.valueOf(f2);

        swamp.setFloras(List.of(flora1, flora2));
        swamp.setDustInch(dust);
        ByteArrayOutputStream outputStream = getOutputStream();
        swamp.describeFlora();
        assertEquals(String.format(Locale.ENGLISH, "Болото Сибирское имеет следующую растительность: %s, %s, " +
                        "Болото Сибирское покрыто пылью в %.1f дюйм толщиной.\r\n",
                flora1.getFloraItem(), flora2.getFloraItem(), dust), outputStream.toString());
    }

    @Test
    public void groundIsSimilarTo() {
        Ground ground = new Ground("Новая");
        ground.setSimilarBiome(new Swamp("Сибирское"));
        ByteArrayOutputStream outputStream = getOutputStream();
        ground.isSimilarTo(false);
        assertEquals("Земля Новая похожа на Болото Сибирское\r\n", outputStream.toString());
    }
}
