package models;

import repositories.Visitor;

public interface Shape {
    public void accept(Visitor visitor);
}
