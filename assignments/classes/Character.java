// ─────────────────────────────────────────────────────────────
//  Character.java  —  ЭЦЭГ КЛАСС (АЛЬ ХЭДИЙН БИЧСЭН)
//  Энэ файлыг өөрчлөхгүй!
//  Warrior, Mage, Rogue класснууд энэ классаас extends хийнэ.
// ─────────────────────────────────────────────────────────────

public class Character {

    protected String name;
    protected int hp;
    protected int maxHp;
    protected int mp;
    protected int maxMp;

    public Character(String name) {
        this.name = name;
        this.hp = 100;
        this.maxHp = 100;
        this.mp = 50;
        this.maxMp = 50;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMp() {
        return mp;
    }

    public void takeDamage(int amount) {
        this.hp = Math.max(0, this.hp - amount);
    }

    @Override
    public String toString() {
        return "⚔️ " + name + " [HP: " + hp + "/" + maxHp + ", MP: " + mp + "/" + maxMp + "]";
    }
}
