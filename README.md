# msp430-
MSP430 :
Initially without pressing any switch, Green LED (P1.4) should blink at a frequency of 0.5 Hz and 50 % duty cycle. The Direction register is set 1 for P1.0 and P1.6 as outputs and P1.3 as input. Switch (S2)-P1.3 is detected by enabling Resistor Enable (P1REN) for P1.3. When Switch (S2)-P1.3 is short pressed blinking is paused. When Switch S2 is pressed for at least 2 seconds, the green LED is switched off and Red LED(LED0) starts blinking at a frequency of 0.5 Hz and 50 % duty cycle. When long pressed for second time, Green LED starts blinking a frequency of 0.5 Hz and 50 % duty cycle and Red LED is turned off. When pressed again, the condition is reversed.

MSP430 ADC:
The aim of the program is to perform ADC using MSP430G2553
