/**
 *
 * as3extensions - ActionScript Utility Classes
 * Copyright (C) 2010-2011, myJerry Developers
 * http://www.myjerry.org/as3extensions
 *
 * The file is licensed under the the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.myjerry.as3extensions.io
{
	import flash.filesystem.FileStream;
	import flash.utils.ByteArray;
	
	/**
	 * An extension of the flash.filesystem.FileStream object that supports
	 * reading lines from the inherent file stream. The objects uses an internal buffer so
	 * as not to hit the performance. The default buffer size is 512 bytes.
	 * 
	 * The object supports only standard methods of the underlying FileStream
	 * object.
	 * 
	 * Usage of the class is only recommended when one tends to read the data line by line
	 * from a file stream. If the readUTFLine() or readMultiByteLine()
	 * methods are not intended to be called, it will be preferable to use the FileStream
	 * object, for performance reasons only.
	 * 
	 * @author Sandeep Gupta
	 * @version 1.0
	 * @since 17 Dec 2010
	 */
	public class FileStreamWithLineReader extends FileStream {
		
		/**
		 * The internal buffer we maintain to be used when working with the
		 * readLine() method.
		 */
		private var buffer:ByteArray = new ByteArray();
		
		/**
		 * The default buffer size.
		 */
		private var bufferSize:uint = 512;
		
		/**
		 * Constructor
		 */
		public function FileStreamWithLineReader() {
			super();
		}
		
		/**
		 * Set the buffer size to the given value. A value of ZERO will reset the buffer size
		 * to a default value of 512 bytes.
		 */
		public function setBufferSize(value:uint):void {
			if(value == 0) {
				// the trace line here is intentional to let developer's know
				// of the default value in debug mode.
				trace('FileStreamWithLineReader: Buffer set to default size of 512 bytes.');
				this.bufferSize = 512;
				return;
			}
			
			this.bufferSize = value;
		} 
		
		/**
		 * Utility method to see if check if we have data in our internal buffer.
		 */
		protected function get hasBuffer():Boolean {
			if(buffer.length == 0) {
				return false;
			}
			
			return true;
		}
		
		/**
		 * Method that returns the number of bytes that can be read in the next read
		 * over the actual file stream. The value is the lesser amongst the buffer size
		 * and the actual number of bytes available in the file stream.
		 */
		protected function get bytesToRead():uint {
			return Math.min(this.bufferSize, super.bytesAvailable);
		}
		
		/**
		 * Method to refill the buffer again with data from the file stream. The buffer is
		 * either filled with the number of bytes as returned by the bytesToRead()
		 * method.
		 */
		protected function refillBuffer():void {
			if(!this.hasBuffer) {
				super.readBytes(buffer, 0, this.bytesToRead);
			}
		}
		
		/**
		 * Method to clean up the current buffer and move back in the original stream by the
		 * extra bytes that were buffered in.
		 */
		protected function undoBuffer():void {
			if(this.hasBuffer) {
				super.position = (super.position - this.buffer.length);
				this.buffer = new ByteArray();
			}
		}
		
		/**
		 * Reads a UTF-8 encoded line from the inherent file stream. The presence of
		 * a line termination character '\n' indicates the end-of-line.
		 */
		public function readUTFLine():String {
			return readMultiByteLine("utf-8");
		}
		
		/**
		 * Reads a line in the specified encoding from the inherent file stream. The presence of
		 * a line termination character '\n' indicates the end-of-line.
		 */
		public function readMultiByteLine(charSet:String):String {
			var toReturn:String = readLine(charSet);
			
			// the following check is a fix when on windows the buffer reads between the values of
			// 13 and 10, which are used to indicate the end of line
			if(toReturn != null && toReturn.charCodeAt(toReturn.length - 1) == 13) {
				return toReturn.substr(0, toReturn.length - 1);
			}
			
			return toReturn;
		}
		
		/**
		 * Method that actually attempts to read a line from the inherent file stream using internal buffers
		 * in the given encoding. If no line termination character is found, the entire stream from the current
		 * position to the end-of-file is returned back.
		 */
		protected function readLine(charSet:String):String {
			var joinedString:String = '';
			var toReturn:String = '';
			var extraReadString:String = ''; 
			do {
				refillBuffer();
				
				var currentReadString:String = this.buffer.readMultiByte(this.buffer.length, charSet);
				
				// try and see if current buffer has enough data to return a line
				var index:int = currentReadString.indexOf('\n');
				if(index != -1) {
					if(index == 0) {
						// indicates that the first character of the current read string is a new line
						// return joined string
						toReturn = joinedString;
					} else {
						toReturn = joinedString + currentReadString.substr(0, index - 1);
					}
					
					extraReadString = currentReadString.substr(index + 1); 
					this.buffer = new ByteArray();
					this.buffer.writeMultiByte(extraReadString, charSet);
					this.buffer.position = 0;
					
					return toReturn;
				}
				
				// check if we have more data to read from the file
				// if not, we can return from this point
				if(this.bytesToRead == 0) {
					this.buffer = new ByteArray();
					return joinedString + currentReadString;
				} 
				
				// no the current buffer does not has enough data
				// move the current read string to joined string
				joinedString += currentReadString;
				this.buffer = new ByteArray();
			} while(true);
			
			return joinedString;
		}
		
		/**
		 * Returns the number of bytes of data available for reading in the input buffer.
		 */
		override public function get bytesAvailable():uint {
			if(this.hasBuffer) {
				return buffer.length + super.bytesAvailable;
			}
			
			return super.bytesAvailable;
		}
		
		/**
		 * The current position in the buffer/file where the next read will happen.
		 */
		override public function get position():Number {
			if(this.hasBuffer) {
				return this.position - buffer.length;
			}
			
			return super.position;
		}
		
		/**
		 * Resets the next reading position in the inherent stream and adjusts the internal buffers
		 * accordingly.
		 */
		override public function set position(value:Number):void {
			if(this.hasBuffer) {
				if(value > this.buffer.length) {
					// empty the buffer
					// and skip in original stream
					buffer = new ByteArray();
					value = value - buffer.length;
					super.position = value;
					return;
				} 
				
				if(value == buffer.length) {
					// empty the buffer
					// no need to skip
					buffer = new ByteArray();
					return;
				}
				
				// we just need to skip inside the buffer
				// get how many positions do we need to skip
				value = this.buffer.length - value;
				var tempBuffer:ByteArray = new ByteArray();
				this.buffer.readBytes(tempBuffer, value);
				this.buffer = tempBuffer;
				return;
			}
			
			super.position = value;
		}
		
		/**
		 * Overriden parent class functions to support buffering
		 */
		
		
		override public function readBoolean():Boolean {
			refillBuffer();
			
			return this.buffer.readBoolean();
		}
		
		override public function readByte():int {
			refillBuffer();
			
			return this.buffer.readByte();
		}
		
		override public function readBytes(bytes:ByteArray, offset:uint=0, length:uint=0):void {
			undoBuffer();
			
			super.readBytes(bytes, offset, length);
		}
		
		override public function readDouble():Number {
			refillBuffer();
			
			return this.buffer.readDouble();
		}
		
		override public function readFloat():Number {
			refillBuffer();
			
			return this.buffer.readFloat();
		}
		
		override public function readInt():int {
			refillBuffer();
			
			return this.buffer.readInt();
		}
		
		override public function readObject():* {
			undoBuffer();
			
			return super.readObject();
		}
		
		override public function readShort():int {
			refillBuffer();
			
			return this.buffer.readShort();
		}
		
		override public function readUnsignedByte():uint {
			refillBuffer();
			
			return this.buffer.readUnsignedByte();
		}
		
		override public function readUnsignedInt():uint {
			refillBuffer();
			
			return this.buffer.readUnsignedInt();
		}
		
		override public function readUnsignedShort():uint {
			refillBuffer();
			
			return this.buffer.readUnsignedShort();
		}
		
		override public function readUTF():String {
			undoBuffer();
			
			return super.readUTF();
		}
		
		override public function readUTFBytes(length:uint):String {
			undoBuffer();
			
			return super.readUTFBytes(length);
		}
	}
}