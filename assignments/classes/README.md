# Lab 9 — Class System (Inheritance)

**Нийт оноо:** 100 | **Сэдэв:** `extends`, `super`, `protected`, IS-A харилцаа

## 🎭 Түүх

Dungeon of OOP-ын гүн рүү ганц баатраар нэвтрэх боломжгүй болж байна. Чамд анги — **Warrior** (хүнд зэвсэг), **Mage** (ид шид), **Rogue** (нууц амт цохих) — хэрэгтэй. Эдгээр бүгд **baатар** (Character) — нэр, HP, MP-тэй — боловч өөрийн онцгой чадвартай. Энэ бол `extends` ба `super(...)`-ын хүч: нийтлэг кодыг эцэг класст бичиж, онцгойг хүүхэд класст үлдээнэ.

---

## 🏛️ Эцэг класс: `Character` (АЛЬ ХЭДИЙН БИЧСЭН)

`Character.java` нь аль хэдийн хэрэгжиж бэлэн болсон. Уг файлыг **өөрчилж БОЛОХГҮЙ**. Энэ нь:

| Талбар | Төрөл | Эрх | Тайлбар |
|--------|--------|------|---------|
| `name` | `String` | `protected` | Баатрын нэр |
| `hp` | `int` | `protected` | Одоогийн HP |
| `maxHp` | `int` | `protected` | Дээд HP |
| `mp` | `int` | `protected` | Одоогийн MP |
| `maxMp` | `int` | `protected` | Дээд MP |

| Method | Тайлбар |
|--------|---------|
| `Character(String name)` | `name`-г оноож, default `hp=100, maxHp=100, mp=50, maxMp=50` тавина |
| `getName(), getHp(), getMp()` | Getter-ууд |
| `takeDamage(int amount)` | `hp -= amount` (0-оос доош үл явах) |
| `toString()` | `"⚔️ [name] [HP: x/max, MP: y/max]"` |

Талбарууд `protected` учир хүүхэд класс дотроос шууд `this.hp`, `this.mp` гэж хандаж болно.

---

## 🟢 Core (60 оноо)

### 1. `Warrior extends Character`

Файл: `assignments/classes/Warrior.java`

```java
public class Warrior extends Character {
    protected boolean shielded;
    // ...
}
```

- Constructor: `Warrior(String name)` → `super(name)` дуудна, дараа нь `this.hp = 150; this.maxHp = 150;` тавина
- `protected boolean shielded` талбар (default false)
- `shieldBash()` method → `shielded = true` (дараагийн цохилтонд хамгаалалт идэвхжинэ)
- `takeDamage(int amount)` method **override** хийнэ:
  - Хэрэв `shielded` бол: хохирлыг **хагас** (`amount / 2`)-д багасгана, `shielded = false` буцаана
  - Үгүй бол: эцэг классын `super.takeDamage(amount)` дуудна

**Жишээ:**
```java
Warrior w = new Warrior("Conan");
// w.getHp() == 150
w.takeDamage(30);    // hp: 150 → 120
w.shieldBash();
w.takeDamage(40);    // hp: 120 → 100 (40/2=20)
w.takeDamage(20);    // hp: 100 → 80 (shield reset хийгдсэн)
```

### 2. `Mage extends Character`

Файл: `assignments/classes/Mage.java`

```java
public class Mage extends Character {
    // ...
}
```

- Constructor: `Mage(String name)` → `super(name)` дуудна, дараа нь `this.hp = 80; this.maxHp = 80; this.mp = 120; this.maxMp = 120;` тавина
- `castFireball(Character target)` method:
  - Хэрэв `this.mp >= 30` бол: `this.mp -= 30` хийгээд `target.takeDamage(40)`-г дуудна
  - Үгүй бол: юу ч хийхгүй (silently ignore)

**Жишээ:**
```java
Mage m = new Mage("Merlin");       // hp=80, mp=120
Warrior enemy = new Warrior("X");  // hp=150
m.castFireball(enemy);             // m.mp: 120→90, enemy.hp: 150→110
```

### 3. `Rogue extends Character`

Файл: `assignments/classes/Rogue.java`

```java
public class Rogue extends Character {
    // ...
}
```

- Constructor: `Rogue(String name)` → `super(name)` дуудна, дараа нь `this.hp = 100; this.maxHp = 100; this.mp = 40; this.maxMp = 40;` тавина
- `sneakAttack(Character target)` method:
  - `target.takeDamage(50)` (критикал цохилт)

**Жишээ:**
```java
Rogue r = new Rogue("Shadow");     // hp=100, mp=40
Mage enemy = new Mage("X");        // hp=80
r.sneakAttack(enemy);              // enemy.hp: 80→30
```

### 4. Бүх subclass constructor-т `super(name)` дуудлагатай байх ёстой

Эцэг классын constructor бусад талбаруудыг initialize хийж байгаа учир `super(name)` **эхний мөр** байна. Үгүй бол Java compile алдаа өгнө.

---

## 🟡 Stretch (30 оноо)

### 5. `Paladin extends Warrior` — 2 түвшний inheritance

Файл: `assignments/classes/Paladin.java`

```java
public class Paladin extends Warrior {
    // ...
}
```

- Constructor: `Paladin(String name)` → `super(name)` (энэ нь `Warrior(name)` руу очно)
  - `hp = 180, maxHp = 180` (Warrior-оос илүү бялдартай)
- `heal(int amount)` method:
  - `this.hp = Math.min(maxHp, hp + amount)`
  - Сөрөг `amount`-д юу ч хийхгүй (silently ignore)
- **`shieldBash()` аяндаа өвлөгдсөн** (Warrior-аас) — дахиж бичихгүй

**Жишээ:**
```java
Paladin p = new Paladin("Arthur");   // hp=180
p.takeDamage(100);                    // hp=80
p.heal(50);                           // hp=130
p.heal(9999);                         // hp=180 (capped)
p.shieldBash();                       // inherited from Warrior
p.takeDamage(40);                     // hp=160 (40/2=20)
```

---

## 🔴 Bonus (10 оноо)

### 6. `Rogue` класс `final` байна

```java
public final class Rogue extends Character {
```

`final` зарласан класс нь `extends` хийгдэх боломжгүй — өөрөөр хэлбэл `Rogue`-аас өвлөгдсөн subclass үүсэхгүй. Reflection тест `Modifier.isFinal(Rogue.class.getModifiers())` шалгана.

### 7. `Character` талбарууд `protected` (өөрчлөгдөхгүй)

Reflection тест `Character` классын `hp` талбар **`protected`** гэдгийг шалгана. Эцэг класст гарт өгсөн кодонд энэ байгаа — өөрчлөхгүй бол автоматаар цэг авна.

---

## 🧪 Тест ажиллуулах

```bash
# Бүх tier
bash scripts/run_tests.sh

# Зөвхөн core
bash scripts/run_tests.sh --tag core

# Зөвхөн stretch
bash scripts/run_tests.sh --tag stretch

# Зөвхөн bonus
bash scripts/run_tests.sh --tag bonus
```

---

## ✅ Шалгуурын жагсаалт (Checklist)

### Core
- [ ] `Warrior extends Character`
- [ ] `Mage extends Character`
- [ ] `Rogue extends Character`
- [ ] Constructor-ууд `super(name)` эхний мөрөнд дуудна
- [ ] Warrior HP=150, Mage HP=80 MP=120, Rogue HP=100 MP=40
- [ ] `Warrior.shieldBash()` ба `takeDamage` override зөв ажиллана
- [ ] `Mage.castFireball(target)` MP-г хасаж, target-т damage өгнө
- [ ] `Rogue.sneakAttack(target)` 50 damage өгнө

### Stretch
- [ ] `Paladin extends Warrior`
- [ ] `Paladin.heal(int)` maxHp-аас хэтрэхгүй
- [ ] `Paladin` нь Warrior-оос `shieldBash` өвлөгдсөн

### Bonus
- [ ] `Rogue` класс `final` зарлагдсан
- [ ] `Character.hp` талбар `protected` (өөрчлөгдөөгүй)

---

## 🚫 Түгээмэл алдаанууд

1. **`super(name)` орхих** — compile алдаа өгнө
2. **`super(name)` эхний мөрөнд байхгүй** — compile алдаа өгнө
3. **`this.hp` төлөв `private` учир хандаж чадахгүй** — эцэг классын талбар `protected` учир хандаж болно
4. **`Warrior.takeDamage` дотор `super.takeDamage` дуудахгүй** — өөрөө `hp -= amount` хийхэд logic давхарлагдана
5. **`Mage.castFireball` MP шалгахгүй** — `mp < 30` байхад ч даах ёсгүй
6. **`Paladin` дотор `Character`-оос шууд extends хийх** — бодлого нь `Warrior`-оос extends хийх (2 түвшний inheritance)
7. **Tests өөрчлөх** — `tests/` хавтсыг хөндөхгүй, тэгвэл PR бүтэлгүйтнэ
