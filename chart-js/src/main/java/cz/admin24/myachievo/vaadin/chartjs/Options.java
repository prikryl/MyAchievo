package cz.admin24.myachievo.vaadin.chartjs;

import java.io.Serializable;

public class Options implements Serializable {

    private static final long serialVersionUID       = 1L;

    // Boolean - Whether we show the scale above or below the chart segments
    public Boolean            scaleOverlay           = true;

    // Boolean - If we want to override with a hard coded scale
    public Boolean            scaleOverride          = false;

    // ** Required if scaleOverride is true **
    // Number - The number of steps in a hard coded scale
    public Number             scaleSteps             = null;
    // Number - The value jump in the hard coded scale
    public Number             scaleStepWidth         = null;
    // Number - The centre starting value
    public Number             scaleStartValue        = null;

    // Boolean - Show line for each value in the scale
    public Boolean            scaleShowLine          = true;

    // String - The colour of the scale line
    public String             scaleLineColor         = "rgba(0;0;0;.1)";

    // Number - The width of the line - in pixels
    public Number             scaleLineWidth         = 1;

    // Boolean - whether we should show text labels
    public Boolean            scaleShowLabels        = true;

    // Interpolated JS string - can access value
    public String             scaleLabel             = "AA <%=value%> BBB";

    // String - Scale label font declaration for the scale label
    public String             scaleFontFamily        = "'Arial'";

    // Number - Scale label font size in pixels
    public Number             scaleFontSize          = 12;

    // String - Scale label font weight style
    public String             scaleFontStyle         = "normal";

    // String - Scale label font colour
    public String             scaleFontColor         = "#666";

    // Boolean - Show a backdrop to the scale label
    public Boolean            scaleShowLabelBackdrop = true;

    // String - The colour of the label backdrop
    public String             scaleBackdropColor     = "rgba(255;255;255;0.75)";

    // Number - The backdrop padding above & below the label in pixels
    public Number             scaleBackdropPaddingY  = 2;

    // Number - The backdrop padding to the side of the label in pixels
    public Number             scaleBackdropPaddingX  = 2;

    // Boolean - Stroke a line around each segment in the chart
    public Boolean            segmentShowStroke      = true;

    // String - The colour of the stroke on each segement.
    public String             segmentStrokeColor     = "#fff";

    // Number - The width of the stroke value in pixels
    public Number             segmentStrokeWidth     = 2;

    // Boolean - Whether to animate the chart or not
    public Boolean            animation              = true;

    // Number - Amount of animation steps
    public Number             animationSteps         = 100;

    // String - Animation easing effect.
    public String             animationEasing        = "easeOutBounce";

    // Boolean - Whether to animate the rotation of the chart
    public Boolean            animateRotate          = true;

    // Boolean - Whether to animate scaling the chart from the centre
    public Boolean            animateScale           = false;

    // Function - This will fire when the animation of the chart is complete.
    public String             onAnimationComplete    = null;
}
