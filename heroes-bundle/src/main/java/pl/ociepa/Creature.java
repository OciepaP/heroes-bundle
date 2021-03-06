package pl.ociepa;

class Creature {

    private final CreatureStatistic stats;
    private int currentHp;
    private boolean counterAttackedInThisTurn;

    Creature(){
        stats = new CreatureStatistic("defName", 1,1,10,100);
    }

    Creature(String aName, int aAttack, int aArmor, int aMaxHp, int aMoveRange) {
        stats = new CreatureStatistic(aName, aAttack, aArmor, aMaxHp, aMoveRange);
        currentHp = stats.getMaxHp();
    }

    void attack(Creature aDefender) {

        if(isAlive()){
            int damageToDeal = calculateDamage(aDefender);
            aDefender.currentHp = aDefender.currentHp - damageToDeal;

            if(!aDefender.counterAttackedInThisTurn){
                int damageTodealInCounterAttack = aDefender.calculateDamage(this);
                currentHp = currentHp - damageTodealInCounterAttack;
                aDefender.counterAttackedInThisTurn = true;
            }

        }
    }

    private int calculateDamage(Creature aDefender) {
        int damageToDeal = this.stats.getAttack() - aDefender.stats.getArmor();
        if (damageToDeal < 0) {
            damageToDeal = 0;
        }
        return damageToDeal;
    }

    private boolean isAlive() {
        return currentHp > 0;
    }

    int getCurrentHp() {
        return currentHp;
    }
}
