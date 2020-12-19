#include <stdio.h>
#define INCHES_PER_POUND 166
#define FREEZING_PT 32.0f
#define SCALE_FACTOR (5.0f / 9.0f)

int main() {
	int input, fahr, h, l, w, x;
	while (input !=4) {
		printf("1. Fahrenheit\n2. Package\n3. Calculate X\n4. Exit\n");
		scanf("%d", &input);
		switch (input) {
			case 1:
				printf("Enter fahrenheit temp: ");
				scanf("%d", &fahr);
				fahrToCels(fahr);
				break;
			case 2:
				printf("Enter height: ");
				scanf("%d", &h);
				printf("Enter length: ");
				scanf("%d", &l);
				printf("Enter width: ");
				scanf("%d", &w);
				packageCalc(h,l,w);
				break;
			case 3:
				printf("3x^5 + 2x^4 - 5x^3 - x^2 + 7x - 6\n");
				printf("Enter the value of x: ");
				scanf("%d", &x);
				calculateX(x);
				break;
			default:
				printf("oops invalid input.\n");
			}
	}
	printf("Thank you for using the menu system. Please come again!");
	return 0;
}
int calculateX(int x) {
	printf("Answer is: %d\n", (3*(x*x*x*x*x) + 2*(x*x*x*x) - 5 * (x*x*x) - (x*x) + 7 * x - 6));
	return 0;
}

int fahrToCels(int fahr) {
	float fahrenheit, celsius;
	fahrenheit = fahr;
	celsius = (fahrenheit - FREEZING_PT) * SCALE_FACTOR;
	printf("Celsius equivalent: %.1f\n", celsius);
	return 0;
}

int packageCalc(int height, int length ,int width) {
	float volume, weight;
	volume = height*length*width;
	weight = volume/INCHES_PER_POUND;
	printf("Dimensions: %dx%dx%d\n",height, length,width);
	printf("Volume (cubic inches): %.2f\n",volume);
	printf("Dimensional weight (pounds): %.2f\n", weight);
	return 0;
}
