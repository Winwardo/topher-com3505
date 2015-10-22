package scenegraph;

import java.util.List;
import java.util.Optional;

import com.jogamp.opengl.GL2;

import main.Vector3;
import renderer.Renderable;

public class Node implements Renderable {
	private Optional<Renderable> toRender;
	private List<Node> children;
	private Vector3 localPosition;
	private Vector3 localRotationAngle;
	private float localRotationAmount;
	private GL2 gl;

	public Node(Optional<Renderable> toRender, List<Node> children, Vector3 localPosition, Vector3 localRotation,
			float localRotationAmount, GL2 gl) {
		super();
		this.toRender = toRender;
		this.children = children;
		this.localPosition = localPosition;
		this.localRotationAngle = localRotation;
		this.localRotationAmount = localRotationAmount;
		this.gl = gl;
	}

	@Override
	public void render() {
		gl.glPushMatrix();
		{
			rotate();
			translate();

			if (toRender.isPresent()) {
				toRender.get().render();
			}

			for (Node child : children) {
				child.render();
			}
		}
		gl.glPopMatrix();
	}

	private void rotate() {
		gl.glRotatef(localRotationAmount, localRotationAngle.x(), localRotationAngle.y(), localRotationAngle.z());
	}

	private void translate() {
		gl.glTranslatef(localPosition.x(), localPosition.y(), localPosition.z());
	}

}
