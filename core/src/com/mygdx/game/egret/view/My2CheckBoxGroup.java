package com.mygdx.game.egret.view;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Administrator on 2017/1/1.
 */

public class My2CheckBoxGroup<T extends My2CheckBox>{
        private final Array<T> buttons = new Array<T>();
        private Array<T> checkedButtons = new Array<T>(1);
        private int minCheckCount, maxCheckCount = 1;
        private boolean uncheckLast = true;
        private T lastChecked;

        public My2CheckBoxGroup() {
            minCheckCount = 1;
        }

        public My2CheckBoxGroup(T... buttons) {
            minCheckCount = 0;
            minCheckCount = 1;

            add(buttons);
        }

        public static void make(My2CheckBox... buttons){
            My2CheckBoxGroup checkBoxGroup = new My2CheckBoxGroup<>();
            checkBoxGroup.add(buttons);
            checkBoxGroup.setMaxCheckCount(1);
        }

        public void add (T button) {
            if (button == null) throw new IllegalArgumentException("button cannot be null.");
            button.buttonGroup = null;
            boolean shouldCheck = button.isChecked() || buttons.size < minCheckCount;
            button.setChecked(false);
            button.buttonGroup = this;
            buttons.add(button);
            button.setChecked(shouldCheck);
        }

        public void add (T... buttons) {
            if (buttons == null) throw new IllegalArgumentException("buttons cannot be null.");
            for (int i = 0, n = buttons.length; i < n; i++)
                add(buttons[i]);
        }

        public void remove (T button) {
            if (button == null) throw new IllegalArgumentException("button cannot be null.");
            button.buttonGroup = null;
            buttons.removeValue(button, true);
            checkedButtons.removeValue(button, true);
        }

        public void remove (T... buttons) {
            if (buttons == null) throw new IllegalArgumentException("buttons cannot be null.");
            for (int i = 0, n = buttons.length; i < n; i++)
                remove(buttons[i]);
        }

        public void clear () {
            buttons.clear();
            checkedButtons.clear();
        }

        /** Sets the first {@link TextButton} with the specified text to checked. */
        public void setChecked (String text) {
            if (text == null) throw new IllegalArgumentException("text cannot be null.");
            for (int i = 0, n = buttons.size; i < n; i++) {
                T button = buttons.get(i);
                if (button != null) {
                    button.setChecked(true);
                    return;
                }
            }
        }

        /** Called when a button is checked or unchecked. If overridden, generally changing button checked states should not be done
         * from within this method.
         * @return True if the new state should be allowed. */
        protected boolean canCheck (T button, boolean newState) {
            if (button.isChecked() == newState) return false;

            if (!newState) {
                // Keep button checked to enforce minCheckCount.
                if (checkedButtons.size <= minCheckCount) return false;
                checkedButtons.removeValue(button, true);
            } else {
                // Keep button unchecked to enforce maxCheckCount.
                if (maxCheckCount != -1 && checkedButtons.size >= maxCheckCount) {
                    if (uncheckLast) {
                        int old = minCheckCount;
                        minCheckCount = 0;
                        lastChecked.setChecked(false);
                        minCheckCount = old;
                    } else
                        return false;
                }
                checkedButtons.add(button);
                lastChecked = button;
            }

            return true;
        }

        /** Sets all buttons' {@link Button#isChecked()} to false, regardless of {@link #setMinCheckCount(int)}. */
        public void uncheckAll () {
            int old = minCheckCount;
            minCheckCount = 0;
            for (int i = 0, n = buttons.size; i < n; i++) {
                T button = buttons.get(i);
                button.setChecked(false);
            }
            minCheckCount = old;
        }

        /** @return The first checked button, or null. */
        public T getChecked () {
            if (checkedButtons.size > 0) return checkedButtons.get(0);
            return null;
        }

        /** @return The first checked button index, or -1. */
        public int getCheckedIndex () {
            if (checkedButtons.size > 0) return buttons.indexOf(checkedButtons.get(0), true);
            return -1;
        }

        public Array<T> getAllChecked () {
            return checkedButtons;
        }

        public Array<T> getButtons () {
            return buttons;
        }

        /** Sets the minimum number of buttons that must be checked. Default is 1. */
        public void setMinCheckCount (int minCheckCount) {
            this.minCheckCount = minCheckCount;
        }

        /** Sets the maximum number of buttons that can be checked. Set to -1 for no maximum. Default is 1. */
        public void setMaxCheckCount (int maxCheckCount) {
            if (maxCheckCount == 0) maxCheckCount = -1;
            this.maxCheckCount = maxCheckCount;
        }

        /** If true, when the maximum number of buttons are checked and an additional button is checked, the last button to be checked
         * is unchecked so that the maximum is not exceeded. If false, additional buttons beyond the maximum are not allowed to be
         * checked. Default is true. */
        public void setUncheckLast (boolean uncheckLast) {
            this.uncheckLast = uncheckLast;
        }
    }
