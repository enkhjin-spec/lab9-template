public class Warrior extends Character {

    protected boolean shielded;

    public Warrior(String name) {
        super(name);
        this.hp = 150;
        this.maxHp = 150;
    }

    public void shieldBash() {
        this.shielded = true;
    }

    @Override
    public void takeDamage(int amount) {
        if (amount <= 0) return;

        if (shielded) {
            super.takeDamage(amount / 2);
            shielded = false;
        } else {
            super.takeDamage(amount);
        }
    }
}