package models;

import shared.Visitor;

public interface Shape {
    public void accept(Visitor visitor);
}
