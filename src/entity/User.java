package entity;

public class User {
    private int id;
    private String name;
    private Sex sex;
    private String favoritePorn;

    public User(int id, String name, Sex sex, String favoritePorn) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.favoritePorn = favoritePorn;
    }

    public int getId() {
        return id;
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
