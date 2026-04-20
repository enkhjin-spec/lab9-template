public class Mage extends Character {

    public Mage(String name) {
        super(name);
        this.hp = 80;
        this.maxHp = 80;
        this.mp = 120;
        this.maxMp = 120;
    }

    public void castFireball(Character target) {
        if (target == null) return;

        if (this.mp >= 30) {
            this.mp -= 30;
            target.takeDamage(40);
        }
    }
}