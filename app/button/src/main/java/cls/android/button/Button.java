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
import androidx.constraintlayout.widget.ConstraintLayout;

public class Button extends FrameLayout {
    public static final int START = 0;
    public static final int END = 1;

    private OnStateChangedListener onSwipeListener;

    private MotionLayout motionLayout;
    private TextView textOneView;
    private TextView textTwoView;
    private ImageView imageView;
    private ConstraintLayout btnInsideParent;
    private View background;

    private String textOne = "Placeholder I";
    private String textTwo = "Placeholder II";
    private Drawable img;
    private boolean isVibrationEnabled = false;
    private MotionLayout.TransitionListener transitionListener;
    private ButtonTextAnimator buttonTextAnimator;


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
        setupAnimator();

    }

    private void setupAnimator() {
        buttonTextAnimator = new ButtonTextAnimator(this);
    }

    private void setupViews() {
        initListener();
        textOneView.setText(textOne);
        textTwoView.setText(textTwo);
        if (img != null)
            imageView.setImageDrawable(img);
    }

    private void initListener() {
        transitionListener = new MotionLayout.TransitionListener() {
            private int i1;
            private int i;

            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                this.i = i;
                this.i1 = i1;
                buttonTextAnimator.notifyTransitionChange(motionLayout,i,i1,v);



            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                if (isVibrationEnabled)
                    HardwareUtil.vibrate(getContext());
                notifyStateChange(motionLayout);


            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) {

            }
        };
        motionLayout.setTransitionListener(transitionListener);

    }

    private void notifyStateChange(MotionLayout motionLayout) {
        if (onSwipeListener != null)
            onSwipeListener
                    .onChange(
                            motionLayout.getCurrentState() == motionLayout.getStartState() ?
                                    START : END
                    );
    }

    private void initViews() {
        motionLayout = findViewById(R.id.motionlayout);
        textOneView = findViewById(R.id.text);
        textTwoView = findViewById(R.id.text2);
        imageView = findViewById(R.id.image);
        btnInsideParent = findViewById(R.id.constraint_swipe_inner_btn);
        background = findViewById(R.id.view);
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

    public MotionLayout.TransitionListener getTransitionListener() {
        return transitionListener;
    }

    public void setTransitionListener(MotionLayout.TransitionListener transitionListener) {
        this.transitionListener = transitionListener;
        motionLayout.setTransitionListener(transitionListener);
    }

    public TextView getTextOneView() {
        return textOneView;
    }

    public void setTextOneView(TextView textOneView) {
        this.textOneView = textOneView;
    }

    public TextView getTextTwoView() {
        return textTwoView;
    }

    public void setTextTwoView(TextView textTwoView) {
        this.textTwoView = textTwoView;
    }

    public ConstraintLayout getBtnInsideParent() {
        return btnInsideParent;
    }

    public void setBtnInsideParent(ConstraintLayout btnInsideParent) {
        this.btnInsideParent = btnInsideParent;
    }

    public View getBackgroundView() {
        return background;
    }

    public void setBackground(View background) {
        this.background = background;
    }
}
