import java.util.concurrent.atomic.AtomicInteger;

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

public class Timer {
    private TimerThread thread = new TimerThread();

    public void start() {
        thread.start();
    }

    public void stop() {
        thread.stop();
    }

    public static class TimerThread extends Thread {
        private AtomicInteger counter = new AtomicInteger(0);

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    counter.incrementAndGet();
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                interrupt();
            }
        }

        public int getCounter() {
            return counter.get();
        }
    }
}