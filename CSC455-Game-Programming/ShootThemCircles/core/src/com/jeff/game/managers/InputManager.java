package com.jeff.game.managers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Disposable;
import com.google.common.base.Preconditions;
import com.jeff.game.util.Function;
import com.jeff.game.util.Subscriber;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to process input events
 */
public class InputManager implements InputProcessor, Disposable {

    private static final String NO_SUCH_SUBBER = "The subscriber you are looking for" +
            "doth noth existen";
    private static final String NO_SUCH_EVENT = "What even is that? I dont know about " +
            "him at all";
    private static final String ALREADY_SUBBED = "Buddy, You've already subbed" +
            "to this";

    public enum EventType {
        CLICK,
        KEYUP,
        KEYDOWN,
        MOUSE_DRAG
    }

    public static InputManager getInstance() {
        if (instance == null)
            instance = new InputManager();
        return instance;
    }

    private static InputManager instance;

    private Map<EventType, Map<Subscriber, Function<Object[], Void>>> events;

    private InputManager() {
        events = new HashMap<EventType, Map<Subscriber, Function<Object[], Void>>>();
        for (EventType type : EventType.values()) {
            events.put(type, new HashMap<Subscriber, Function<Object[], Void>>());
        }
    }

    public void sub(EventType type, Subscriber subber, Function<Object[], Void> function) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(subber);
        Preconditions.checkNotNull(function);
        Preconditions.checkArgument(events.containsKey(type), NO_SUCH_EVENT);

        Map<Subscriber, Function<Object[], Void>> subscribers = events.get(type);
        Preconditions.checkArgument(!subscribers.containsKey(subber), ALREADY_SUBBED);

        subscribers.put(subber, function);
    }

    public void unSubEvent(EventType type, Subscriber subber) {
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(subber);
        Preconditions.checkArgument(events.containsKey(type), NO_SUCH_EVENT);
        Map<Subscriber, Function<Object[], Void>> subscribers = events.get(type);
        Preconditions.checkArgument(subscribers.containsKey(subber), NO_SUCH_SUBBER);
        subscribers.remove(subber);
    }

    public void unSubAll(Subscriber subber) {
        Preconditions.checkNotNull(subber);
        for (Map<Subscriber, Function<Object[], Void>> map : events.values()) {
            if (map.containsKey(subber)) {
                map.remove(subber);
            }
        }
    }

    @Override
    public void dispose() {
        events = null;
        instance = null;
    }

    @Override
    public boolean keyDown(int keycode) {
        final Object[] args = {keycode};
        doCallBacks(events.get(EventType.KEYDOWN).values(), args);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        final Object[] args = {keycode};
        doCallBacks(events.get(EventType.KEYUP).values(), args);
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        final Object[] args = {screenX, screenY, pointer, button};
        doCallBacks(events.get(EventType.CLICK).values(), args);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        final Object[] args = {screenX, screenY};
        doCallBacks(events.get(EventType.MOUSE_DRAG).values(), args);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return true;
    }

    private void doCallBacks(Collection<Function<Object[], Void>> callBacks, Object[] args) {
        for (Function<Object[], Void> callBack : callBacks) {
            callBack.apply(args);
        }
    }

}
