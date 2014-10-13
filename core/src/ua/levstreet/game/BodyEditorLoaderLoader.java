package ua.levstreet.game;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class BodyEditorLoaderLoader
		extends
		AsynchronousAssetLoader<BodyEditorLoader, AssetLoaderParameters<BodyEditorLoader>> {
	private BodyEditorLoader bodyEditorLoader;

	public BodyEditorLoaderLoader() {
		super(new InternalFileHandleResolver());
		World.getVelocityThreshold();
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName,
			FileHandle file, AssetLoaderParameters<BodyEditorLoader> parameter) {
		bodyEditorLoader = new BodyEditorLoader(file);
	}

	@Override
	public BodyEditorLoader loadSync(AssetManager manager, String fileName,
			FileHandle file, AssetLoaderParameters<BodyEditorLoader> parameter) {
		try {
			return bodyEditorLoader;
		} finally {
			bodyEditorLoader = null;
		}
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName,
			FileHandle file, AssetLoaderParameters<BodyEditorLoader> parameter) {
		Array<AssetDescriptor> array = new Array<AssetDescriptor>();
		return array;
	}
}
