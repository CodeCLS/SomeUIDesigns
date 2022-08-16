package cls.android.button;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.content.ContextCompat;

public class ButtonTextAnimator {
    private final Button button;

    public ButtonTextAnimator(Button button) {
        this.button = button;
    }

    public void notifyTransitionChange(MotionLayout motionLayout, int startId, int endId, float percentageOfAnimDone) {
        TextView start = null;
        TextView end = null;
        Log.d(TAG, "notifyTransitionChange: " + percentageOfAnimDone);
        if (startId == R.id.end){
            start = button.getTextTwoView();
            end = button.getTextOneView();

        }
        else if(startId == R.id.start){
            start = button.getTextOneView();
            end = button.getTextTwoView();
        }
        taskTextAnim(motionLayout,percentageOfAnimDone,startId,endId,start,1);
        taskTextAnim(motionLayout,percentageOfAnimDone,startId,endId,end,-1);







    }

    private void taskAddTextTwo(MotionLayout motionLayout, float percentageOfAnimDone, int i1, int v, TextView add) {


    }

    private void taskTextAnim(MotionLayout motionLayout, float percentageOfAnimDone, int i1, int v, TextView remove, int i) {
        Log.d(TAG, "taskRemoveTextOne:123 " + percentageOfAnimDone);
        float textWidthOne = button.getTextOneView().getPaint().measureText(button.getTextOne());
        float charWidth =
                remove.getPaint().measureText(remove.getText().toString()) / remove.getText().length();

        float parentAnimWidth = button.getBackgroundView().getWidth() -
                (2* button.getResources().getDimensionPixelSize(R.dimen.margin_regular));

        double percentageTextOneInsideOfParent = 100/parentAnimWidth * textWidthOne;
        String s = remove.getText().toString();

        double startOfTextOneFromStart =  parentAnimWidth * ((1-percentageTextOneInsideOfParent /100)/2);
        double animBtnPositionX = percentageOfAnimDone * parentAnimWidth;
        double diff = animBtnPositionX - startOfTextOneFromStart;
        Spannable spannable = new SpannableString(s);
        Log.d(TAG, "taskRemoveTextOne: " + percentageOfAnimDone);
        if (diff > 0){
            Log.d(TAG, "taskRemoveTextOne:1232 " + diff +" " + percentageOfAnimDone);
            int pos = Math.toIntExact(Math.round(diff / charWidth));
            if (pos > s.length()) {
                if(i == 1) {
                    spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#00000000")), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                else{
                    spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(button.getContext(), R.color.black_original)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                }
                remove.setText(spannable);
                return;
            }
            if (i == 1) {
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#00000000")), 0, pos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(button.getContext(), R.color.white_original)), pos, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            else{
                spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(button.getContext(),R.color.black_original)), 0, pos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#00000000")), pos, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            remove.setText(spannable);

        }
        else{
            if (i == 1) {
                spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(button.getContext(), R.color.white_original)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            else{
                spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#00000000")), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            remove.setText(spannable);

        }



    }

    private static final String TAG = "ButtonTextAnimator";
}
