/************************************************************************/
/* CAN Bus Communication Using Tiva board                               */
/*                                                                      */
/* README FILE:                                                         */
/* The aim of the program is to interface two Tiva C series Launchpad   */
/* (TM4C123GH6PM) with a CAN bus protocol (using MCP2551) to provide    */
/* two-way communication between two computers.                         */
/*                                                                      */
/* Board used - TI's TM4C123GH6PM                                       */
/* IDE - Code Composer Studio 7.3.0                                     */
/* TivaWare_C_Series-2.1.4.178                                          */
/*                                                                      */
/* Steps to Compile :                                                   */
/* 1. Download CCS and Tivaware.                                        */
/* 2. Open a new CCS project (File/new/project).                        */
/* 3. Add the required header files in Project Explorer or you can also */
/* link the files by adding the path in ARM Compiler/Include Options and*/
/* by adding the driver.lib in ARM Linker/File Search Path.             */
/* 4. This program should be kept in main file.                         */
/* 5. Build the program and check for errors.                           */
/* 6. Connect the two tiva boards through USB                           */
/* 7. Open Tera term for both transmitter and receiver and select the   */
/* comm port for 'serial' option.                                       */
/* 8. Change the baud rate in Setup/Serial port. The baud rate should be*/
/* set to 115200 as we have mentioned in the program.                   */
/* 9. If there are no errors found in the program, debug and run the    */
/* code by clicking the play button.                                    */
/* 10. Type anything in computer 1 and it will be displayed in computer */
/* and vice versa.                                                      */
/************************************************************************/
