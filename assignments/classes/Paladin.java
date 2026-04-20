public class Paladin extends Warrior {

    public Paladin(String name) {
        super(name);
        this.hp = 180;
        this.maxHp = 180;
    }

    public void heal(int amount) {
        if (amount <= 0) return;
        this.hp = Math.min(this.maxHp, this.hp + amount);
    }
}