// ─────── 🟢 Core (60 оноо) ───────
// Warrior extends Character

public class Warrior extends Character {

    // TODO: protected boolean shielded — default false

    // TODO: Constructor
    // public Warrior(String name) {
    //     super(name);             // <-- эхний мөр заавал байх
    //     this.hp = 150;
    //     this.maxHp = 150;
    // }

    // TODO: shieldBash() → void
    // - shielded = true (дараагийн takeDamage-ийг хагасладаг)

    // TODO: takeDamage(int amount) → void  [override]
    // - Хэрэв shielded:
    //     super.takeDamage(amount / 2);
    //     shielded = false;
    // - Үгүй бол:
    //     super.takeDamage(amount);
}
