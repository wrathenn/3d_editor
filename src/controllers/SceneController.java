package controllers;

import exceptions.ExistedNameException;
import exceptions.NotExistedNameException;
import io.FileSceneReader;
import libs.HashMapUnique;
import models.draw.Camera;
import models.scene.Edge;
import models.scene.Point;
import models.scene.Polygon;
import models.scene.Vector;
import repositories.SceneRepository;

import java.awt.*;
import java.io.*;
import java.util.UUID;

public class SceneController {

    private SceneRepository sceneRepository;

    // ----- Конструкторы ----- //

    public SceneController(SceneRepository sceneStore) {
        this.sceneRepository = sceneStore;
    }

    // ----- Сеттеры и Геттеры ----- //

    public void setSceneRepository(SceneRepository sceneRepository) {
        this.sceneRepository = sceneRepository;
    }

    public SceneRepository getSceneRepository() {
        return sceneRepository;
    }

    public Polygon getPolygonById(UUID polyId) {
        return sceneRepository.getPolygon(polyId);
    }

    // ----- Методы ----- //

    public void add(Point p) throws ExistedNameException {
        sceneRepository.add(p);
    }

    public void add(Polygon p) {
        sceneRepository.add(p);
    }

    public void delete(Point p) {
        sceneRepository.delete(p);
    }

    public void delete(Edge e) {
        sceneRepository.delete(e);
    }

    public void delete(Polygon p) {
        sceneRepository.delete(p);
    }

    public Point findPoint(String name) throws NotExistedNameException {
        return sceneRepository.getPoint(name);
    }

    public Camera getCamera() {
        return sceneRepository.getCamera();
    }

    public SceneRepository readFromFile(String filename) throws IOException {
        FileSceneReader reader = new FileSceneReader(filename);
        SceneRepository newRepo = new SceneRepository();

        reader.readHeader("POINTS:");
        int pointsCount = reader.readInt();
        for (int i = 0; i < pointsCount; i++) {
            Point p = reader.readPoint();
            newRepo.add(p);
        }

        reader.readHeader("POLYGONS:");
        int polygonsCount = reader.readInt();
        for (int i = 0; i < polygonsCount; i++) {
            String[] pNames = reader.readPolygonPoints();
            Color polyColor = reader.readPolygonColor();

            Point[] points = new Point[pNames.length];
            int pI = 0;
            for (String pName : pNames) {
                Point p = newRepo.getPoint(pName);
                if (p == null) {
                    throw new IOException(String.format(
                            "Ошибка при чтении из файла. Точка \"%s\" не существует.", pName
                    ));
                }
                points[pI++] = newRepo.getPoint(pName);
            }

            Polygon newPoly = new Polygon(points, polyColor);
            newRepo.add(newPoly);
        }

        reader.readHeader("CAMERA:");
        Vector position = reader.readVector();
        Vector target = reader.readVector();
        double yaw = reader.readDouble();
        double pitch = reader.readDouble();
        int screenDistance = reader.readInt();
        Camera newCamera = new Camera(position, target, yaw, pitch, screenDistance);
        newRepo.setCamera(newCamera);

        return newRepo;
    }
}
