#Troubleshooting

##Symptom

You receive a warning regarding the target version.

![alt text](https://github.com/microsoft-dpe/wa-toolkit-android/raw/develop/docs/img/3_1.png "Title")

##Solution

1. Right-click each project and select **Properties -> Android**. Then select the Android 2.3.3 version as the project build target.

	![alt text](https://github.com/microsoft-dpe/wa-toolkit-android/raw/develop/docs/img/3_2.png "Title")

##Symptom

You receive the ***‘Android requires compiler compliance level 5.0 or 6.0. Found '1.4' instead. Please use Android Tools > Fix Project Properties.’*** warning.

##Solution

1.	Locate the projects with warnings on the Package Explorer and Right-click **Android Tools -> Fix Project Properties**.

	![alt text](https://github.com/microsoft-dpe/wa-toolkit-android/raw/develop/docs/img/3_3.png "Title")

##Symptom
	
You receive a ***“project ‘A’ is missing required Java project: ‘B’”*** error.

![alt text](https://github.com/microsoft-dpe/wa-toolkit-android/raw/develop/docs/img/3_4.png "Title")

##Solution

1.	Right-click the project with missing references and choose **Properties**.
2.	Click the **Java Build Path** item on the left menu, go to the **Projects** tab, select the missing project and click **Remove** button.
	
	![alt text](https://github.com/microsoft-dpe/wa-toolkit-android/raw/develop/docs/img/3_5.png "Title")

3.	Click **Add…** and select the missing project from the list.
	
	![alt text](https://github.com/microsoft-dpe/wa-toolkit-android/raw/develop/docs/img/3_6.png "Title")

##Symptom
	
You receive several warnings regarding **minSdkVersion**.

![alt text](https://github.com/microsoft-dpe/wa-toolkit-android/raw/develop/docs/img/3_7.png "Title")

##Solution

1.	Double-click each warning; it will open the **AndroidManifest.xml** file.
2.	Click the **AndroidManifest.xml** label to display the manifest in raw XML format.
	
	![alt text](https://github.com/microsoft-dpe/wa-toolkit-android/raw/develop/docs/img/3_8.png "Title")
	 
3.	Update the **android:minSdkVersion** to 10.

	![alt text](https://github.com/microsoft-dpe/wa-toolkit-android/raw/develop/docs/img/3_9.png "Title")