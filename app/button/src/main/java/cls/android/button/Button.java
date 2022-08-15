package cls.android.button;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;

public class Button extends FrameLayout {
    public static final int START = 0;
    public static final int END = 1;

    private OnStateChangedListener onSwipeListener;

    private MotionLayout motionLayout;
    private TextView textOneView;
    private TextView textTwoView;
    private ImageView imageView;

    private String textOne = "Placeholder I";
    private String textTwo = "Placeholder II";
    private Drawable img;
    private boolean isVibrationEnabled = false;


    public Button(@NonNull Context context) {
        super(context);
        init();
    }

    public Button(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Button(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public Button(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_button_swipe,this);
        initViews();
        setupViews();
    }

    private void setupViews() {
        initListener();
        textOneView.setText(textOne);
        textTwoView.setText(textTwo);
        if (img != null)
            imageView.setImageDrawable(img);
    }

    private void initListener() {
        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if (isVibrationEnabled)
                    HardwareUtil.vibrate(getContext());
                if (onSwipeListener != null)
                    onSwipeListener
                            .onChange(
                                    motionLayout.getCurrentState() == motionLayout.getStartState() ?
                                            START : END
                            );

            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        });

    }

    private void initViews() {
        motionLayout = findViewById(R.id.motionlayout);
        textOneView = findViewById(R.id.text);
        textTwoView = findViewById(R.id.text2);
        imageView = findViewById(R.id.image);
    }

    public void setOnSwipeListener(OnStateChangedListener onSwipeListener){
        this.onSwipeListener = onSwipeListener;
    }

    public void isVibrationEnabled(boolean b) {
        isVibrationEnabled = b;
    }

    public interface OnStateChangedListener{
        void onChange(int i);

    }

    public String getTextOne() {
        return textOne;
    }

    public void setTextOne(String textOne) {
        this.textOne = textOne;

        setupViews();
        postInvalidate();

    }

    public String getTextTwo() {
        return textTwo;
    }

    public void setTextTwo(String textTwo) {
        this.textTwo = textTwo;

        setupViews();
        postInvalidate();

    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
        imageView.setImageDrawable(img);
        postInvalidate();

    }
}
