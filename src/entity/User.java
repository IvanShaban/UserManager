package entity;

public class User {
    private static int count = 1;
    private int id;
    private String name;
    private Sex sex;
    private String favoritePorn;

    public User(String name, Sex sex, String favoritePorn) {
        this.id = count;
        this.name = name;
        this.sex = sex;
        this.favoritePorn = favoritePorn;
        count++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getFavoritePorn() {
        return favoritePorn;
    }

    public void setFavoritePorn(String favoritePorn) {
        this.favoritePorn = favoritePorn;
    }

    @Override
    public String toString() {
        return "Пользователь: " +
                "id = " + id +
                "; имя = '" + name + '\'' +
                "; пол = '" + sex.getTranslation() + '\'' +
                "; любимое порно = '" + favoritePorn + '\'';
    }
}
