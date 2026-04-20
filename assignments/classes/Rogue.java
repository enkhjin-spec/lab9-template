public final class Rogue extends Character {

    public Rogue(String name) {
        super(name);
        this.hp = 100;
        this.maxHp = 100;
        this.mp = 40;
        this.maxMp = 40;
    }

    public void sneakAttack(Character target) {
        if (target == null) return;
        target.takeDamage(50);
    }
}