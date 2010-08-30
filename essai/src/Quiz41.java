import java.util.Random;

/*
* Copyright 2010 Sewatech
*
* This file is part of LogWEx.
*
* LogWEx is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as
* published bythe Free Software Foundation, in version 3 of the
* License.
*
* LogWEx is distributed in the hope that it will be useful, but
* WITHOUT ANY WARRANTY; without even the implied warranty
* of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General
* Public License along with LogWEx.  If not, see
* <http://www.gnu.org/licenses/>.
*/

public class Quiz41 {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.start();
        Thread.sleep(new Random().nextInt(2000));
        timer.stop();
    }
}