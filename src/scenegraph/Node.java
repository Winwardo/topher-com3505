package scenegraph;

import java.util.List;
import java.util.Optional;

import main.Vector3;
import renderer.Renderable;

public class Node implements Renderable {
	private Optional<Renderable> toRender;
	private List<Node> children;
	private Vector3 localPosition;
	private Vector3 localRotation;

	public Node(Optional<Renderable> toRender, List<Node> children, Vector3 localPosition, Vector3 localRotation) {
		super();
		this.toRender = toRender;
		this.children = children;
		this.localPosition = localPosition;
		this.localRotation = localRotation;
	}

	@Override
	public void render() {
		if (toRender.isPresent()) {
			toRender.get().render();
		}

		for (Node child : children) {
			child.render();
		}
	}

}
