import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@DisplayName("Lab 9: Class System (Inheritance)")
public class ClassesTest {

    // ==================== 🟢 CORE ====================

    @Test
    @Tag("core")
    @DisplayName("Warrior нь Character-ээс extends хийсэн")
    void warriorExtendsCharacter() {
        Warrior w = new Warrior("Conan");
        assertTrue(w instanceof Character, "Warrior нь Character-ийн subclass байх ёстой");
        assertEquals(Character.class, Warrior.class.getSuperclass(),
            "Warrior-ын шууд эцэг нь Character байх ёстой");
    }

    @Test
    @Tag("core")
    @DisplayName("Mage нь Character-ээс extends хийсэн")
    void mageExtendsCharacter() {
        Mage m = new Mage("Merlin");
        assertTrue(m instanceof Character, "Mage нь Character-ийн subclass байх ёстой");
        assertEquals(Character.class, Mage.class.getSuperclass(),
            "Mage-ын шууд эцэг нь Character байх ёстой");
    }

    @Test
    @Tag("core")
    @DisplayName("Rogue нь Character-ээс extends хийсэн")
    void rogueExtendsCharacter() {
        Rogue r = new Rogue("Shadow");
        assertTrue(r instanceof Character, "Rogue нь Character-ийн subclass байх ёстой");
        assertEquals(Character.class, Rogue.class.getSuperclass(),
            "Rogue-ийн шууд эцэг нь Character байх ёстой");
    }

    @Test
    @Tag("core")
    @DisplayName("Warrior эхний HP 150")
    void warriorInitialStats() {
        Warrior w = new Warrior("Conan");
        assertEquals("Conan", w.getName());
        assertEquals(150, w.getHp(), "Warrior HP 150 байх ёстой");
    }

    @Test
    @Tag("core")
    @DisplayName("Mage эхний HP 80, MP 120")
    void mageInitialStats() {
        Mage m = new Mage("Merlin");
        assertEquals("Merlin", m.getName());
        assertEquals(80, m.getHp(), "Mage HP 80 байх ёстой");
        assertEquals(120, m.getMp(), "Mage MP 120 байх ёстой");
    }

    @Test
    @Tag("core")
    @DisplayName("Rogue эхний HP 100, MP 40")
    void rogueInitialStats() {
        Rogue r = new Rogue("Shadow");
        assertEquals("Shadow", r.getName());
        assertEquals(100, r.getHp(), "Rogue HP 100 байх ёстой");
        assertEquals(40, r.getMp(), "Rogue MP 40 байх ёстой");
    }

    @Test
    @Tag("core")
    @DisplayName("Warrior.shieldBash дараагийн takeDamage-ийг хагасладаг")
    void warriorShieldBashHalvesNextDamage() {
        Warrior w = new Warrior("Conan");
        w.takeDamage(30);
        assertEquals(120, w.getHp(), "30 damage-ийн дараа hp=120");

        w.shieldBash();
        w.takeDamage(40);
        assertEquals(100, w.getHp(), "Shielded үед 40/2=20 damage → hp=100");

        w.takeDamage(20);
        assertEquals(80, w.getHp(), "Shield reset болж, 20 бүтэн damage → hp=80");
    }

    @Test
    @Tag("core")
    @DisplayName("Mage.castFireball MP-г хасаж, target-т damage өгнө")
    void mageCastFireballReducesMpAndDamagesTarget() {
        Mage m = new Mage("Merlin");         // hp=80, mp=120
        Warrior target = new Warrior("Dummy"); // hp=150

        m.castFireball(target);
        assertEquals(90, m.getMp(), "Mage MP 120→90 болох ёстой");
        assertEquals(110, target.getHp(), "Target HP 150→110 болох ёстой");

        // Дахиад 3 удаа тайвшруулна (90→60→30→0)
        m.castFireball(target);
        m.castFireball(target);
        m.castFireball(target);
        assertEquals(0, m.getMp(), "4 fireball-ын дараа MP=0");

        // MP хүрэлцэхгүй үед юу ч болохгүй
        int targetHpBefore = target.getHp();
        m.castFireball(target);
        assertEquals(0, m.getMp(), "MP=0-ээс цаашаа хасагдах ёсгүй");
        assertEquals(targetHpBefore, target.getHp(),
            "MP<30 үед target damage аваагүй байх ёстой");
    }

    @Test
    @Tag("core")
    @DisplayName("Rogue.sneakAttack 50 damage өгнө")
    void rogueSneakAttackDealsFiftyDamage() {
        Rogue r = new Rogue("Shadow");
        Mage target = new Mage("Merlin"); // hp=80
        r.sneakAttack(target);
        assertEquals(30, target.getHp(), "80 - 50 = 30 байх ёстой");
    }

    // ==================== 🟡 STRETCH ====================

    @Test
    @Tag("stretch")
    @DisplayName("Paladin нь Warrior-ээс extends хийсэн (2 түвшний inheritance)")
    void paladinExtendsWarrior() {
        Paladin p = new Paladin("Arthur");
        assertTrue(p instanceof Warrior, "Paladin нь Warrior байх ёстой");
        assertTrue(p instanceof Character, "Paladin нь Character-ын үр хүүхэд байх ёстой");
        assertEquals(Warrior.class, Paladin.class.getSuperclass(),
            "Paladin-ы шууд эцэг нь Warrior байх ёстой (Character биш)");
    }

    @Test
    @Tag("stretch")
    @DisplayName("Paladin.heal maxHp-аас хэтрэхгүй")
    void paladinHealCapsAtMaxHp() {
        Paladin p = new Paladin("Arthur");     // hp=180, maxHp=180
        p.takeDamage(100);                      // hp=80
        assertEquals(80, p.getHp());

        p.heal(50);                             // hp=130
        assertEquals(130, p.getHp());

        p.heal(9999);                           // hp should cap at 180
        assertEquals(180, p.getHp(), "maxHp=180-аас хэтрэх ёсгүй");
    }

    @Test
    @Tag("stretch")
    @DisplayName("Paladin нь Warrior-аас shieldBash өвлөсөн")
    void paladinInheritsShieldBash() {
        Paladin p = new Paladin("Arthur");     // hp=180
        p.takeDamage(30);                       // hp=150
        assertEquals(150, p.getHp());

        p.shieldBash();                         // inherited method — заавал байх
        p.takeDamage(40);                       // shielded → 20 damage → hp=130
        assertEquals(130, p.getHp(),
            "Paladin нь Warrior.shieldBash-ийг өвлөж ашиглах ёстой");
    }

    // ==================== 🔴 BONUS ====================

    @Test
    @Tag("bonus")
    @DisplayName("Rogue класс final байна")
    void rogueIsFinal() {
        assertTrue(Modifier.isFinal(Rogue.class.getModifiers()),
            "Rogue класс 'final' зарлагдсан байх ёстой");
    }

    @Test
    @Tag("bonus")
    @DisplayName("Character.hp талбар protected байна")
    void characterHpIsProtected() throws Exception {
        Field hp = Character.class.getDeclaredField("hp");
        assertTrue(Modifier.isProtected(hp.getModifiers()),
            "Character.hp талбар protected байх ёстой (private биш)");
    }
}
