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
	private Vector3 localRotation;
	private GL2 gl;

	public Node(Optional<Renderable> toRender, List<Node> children, Vector3 localPosition, Vector3 localRotation,
			GL2 gl) {
		super();
		this.toRender = toRender;
		this.children = children;
		this.localPosition = localPosition;
		this.localRotation = localRotation;
		this.gl = gl;
	}

	@Override
	public void render() {
		gl.glPushMatrix();
		{
			// rotate and translate
			if (toRender.isPresent()) {
				toRender.get().render();
			}

			for (Node child : children) {
				child.render();
			}
		}
		gl.glPopMatrix();
	}

}
