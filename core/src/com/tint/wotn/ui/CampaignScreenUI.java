package com.tint.wotn.ui;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tint.wotn.Core;
import com.tint.wotn.screens.Screens;

public class CampaignScreenUI extends UserInterface {

	private Table briefingTable;
	private Table storyPageTable;
	private Label nameLabel;
	private Label legendLabel;
	private Label storyText;

	public CampaignScreenUI(Skin skin) {
		super(skin);
		stage = new Stage(new ExtendViewport(1920, 1080), Core.INSTANCE.batch);
	}

	@Override
	public void load() {
		stage.clear();
		
		briefingTable = new Table(skin);
		briefingTable.setFillParent(true);
		briefingTable.pad(30);
		briefingTable.setVisible(false);
		
		storyPageTable = new Table(skin);
		storyPageTable.setFillParent(true);
		storyPageTable.pad(30);
		storyPageTable.setVisible(true);
		storyPageTable.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				InputEvent input = (InputEvent) event;
				if (input != null) {
					if (input.getType() == Type.touchDown) {
						boolean endOfBook = !Core.INSTANCE.story.nextPage();
						boolean newParagraph = Core.INSTANCE.story.getCurrentPageIndex() == 0;
						String currentPageText = Core.INSTANCE.story.getCurrentPage().getText();

						if (endOfBook || newParagraph)
							getStorage().storeData("Data", "Page visible", "false");
						else
							getStorage().storeData("Data", "Page text", currentPageText);

						return true;
					}
				}
				return false;
			}
		});
		
		
		Table storyTextTable = new Table(skin);
		
		Table baseUITable = new Table();
		baseUITable.setFillParent(true);
		baseUITable.pad(30);

		legendLabel = new Label("", skin);
		legendLabel.setAlignment(Align.center);
		
		nameLabel = new Label("", skin);
		nameLabel.setFontScale(3);
		nameLabel.setAlignment(Align.center);
		
		storyText = new Label("", skin);
		storyText.setWrap(true);
		
		ImageTextButtonStyle backBtnStyle = getButtonStyle();
		ImageTextButton backBtn = new ImageTextButton("Back", backBtnStyle);
		backBtn.getLabelCell().padBottom(backBtnStyle.font.getCapHeight() / 2);
		backBtn.pad(10);
		backBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				Core.INSTANCE.screenSystem.setScreenToEnter(Screens.MAIN_MENU);
			}
		});

		ImageTextButtonStyle playBtnStyle = getButtonStyle();
		final ImageTextButton playBtn = new ImageTextButton("Play", playBtnStyle);
		playBtn.getLabelCell().padBottom(playBtnStyle.font.getCapHeight() / 2);
		playBtn.pad(10);
		playBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				getStorage().storeData("Data", "Briefing visible", "false");
				getStorage().storeData("Data", "Page visible", "true");
				Core.INSTANCE.levelSystem.enterCurrentLevel();
				Core.INSTANCE.screenSystem.setScreenToEnter(Screens.BATTLE);
			}
		});

		ImageTextButtonStyle cancelBtnStyle = getButtonStyle();
		ImageTextButton cancelBtn = new ImageTextButton("Cancel", cancelBtnStyle);
		cancelBtn.getLabelCell().padBottom(cancelBtnStyle.font.getCapHeight() / 2);
		cancelBtn.pad(10);
		cancelBtn.addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 1.0f, false);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Core.INSTANCE.audioSystem.playSound("sounds/btn_click.wav", 0.25f, false);
				Core.INSTANCE.world.unselectQuest();
				getStorage().storeData("Data", "Briefing visible", "false");
				getStorage().storeData("Data", "Page visible", "false");
			}
		});
		
		storyTextTable.add(storyText).pad(10).top().width(1920 / 3.0f);
		storyTextTable.row();
		storyTextTable.add(new Label("", skin)).expand().fill();
		
		storyPageTable.add(storyTextTable).width(1920 / 3.0f).height(3.0f / 4.0f * 1080);
		
		briefingTable.add(nameLabel).expandX().pad(10).colspan(2);
		briefingTable.row();
		briefingTable.add(legendLabel).expand().fill().pad(10, 200, 10, 200).colspan(2);
		briefingTable.row();
		briefingTable.add(playBtn).pad(0, 0, playBtn.getHeight() * 3, 0);
		briefingTable.add(cancelBtn).pad(0, 0, cancelBtn.getHeight() * 3, 0);
		briefingTable.row();
		briefingTable.add().bottom();
		
		baseUITable.add(backBtn).align(Align.bottomRight);
		
		stage.addActor(storyPageTable);
		stage.addActor(briefingTable);
		stage.addActor(baseUITable);
		
		getStorage().addDataset("Data", new HashMap<String, String>());
		getStorage().storeData("Data", "Briefing visible", "false");
		getStorage().storeData("Data", "Page visible", "false");
	}
	
	private ImageTextButtonStyle getButtonStyle() {
		ImageTextButtonStyle btnStyle = new ImageTextButtonStyle();
		TextureAtlas atlas = Core.INSTANCE.assets.getTextureAtlas("textures/packed/WarriorsOfTheNorth.atlas");
		TextureRegionDrawable normal = new TextureRegionDrawable(atlas.findRegion("small_menu_button"));
		TextureRegionDrawable over = new TextureRegionDrawable(atlas.findRegion("small_menu_button_over"));
		btnStyle.font = Core.INSTANCE.assets.getFont("menu.ttf");
		btnStyle.fontColor = Color.BLACK;
		btnStyle.downFontColor = Color.WHITE;
		btnStyle.over = over;
		btnStyle.up = normal;
		btnStyle.down = normal;
		
		return btnStyle;
	}
	
	@Override
	public void update(float delta) {
		boolean pageVisible = Boolean.parseBoolean(getStorage().getData("Data", "Page visible"));
		if (pageVisible != storyPageTable.isVisible())
			storyPageTable.setVisible(pageVisible);

		String text = getStorage().getData("Data", "Page text");
		if (!text.equals(storyText.getText()))
			storyText.setText(text);

		boolean briefingVisible = Boolean.parseBoolean(getStorage().getData("Data", "Briefing visible"));
		if (briefingVisible != briefingTable.isVisible()) {
			nameLabel.setText(getStorage().getData("Data", "Briefing name"));
			legendLabel.setText(getStorage().getData("Data", "Briefing legend"));
			briefingTable.setVisible(briefingVisible);
		}
	}
}
