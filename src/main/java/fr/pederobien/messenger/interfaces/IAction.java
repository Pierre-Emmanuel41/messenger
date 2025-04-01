package fr.pederobien.messenger.interfaces;

public interface IAction {

	public class ActionArgs {
		private IProtocolConnection connection;
		private int messageID;
		private Object payload;

		/**
		 * Creates an argument for an action to execute when an unexpected message is
		 * received from the remote.
		 * 
		 * @param connection The connection that has received the unexpected message.
		 * @param requestID  The identifier of the message received from the remote.
		 * @param payload    The payload received from the remote.
		 */
		public ActionArgs(IProtocolConnection connection, int messageID, Object payload) {
			this.connection = connection;
			this.messageID = messageID;
			this.payload = payload;
		}

		/**
		 * @return The connection that has received the unexpected message from the
		 *         remote.
		 */
		public IProtocolConnection getConnection() {
			return connection;
		}

		/**
		 * @return The identifier of the message received from the remote.
		 */
		public int getMessageID() {
			return messageID;
		}

		/**
		 * @return The payload received from the remote.
		 */
		public Object getPayload() {
			return payload;
		}
	}

	/**
	 * Action to execute when a request has been received from the remote. The input
	 * identifier does not correspond to the
	 * 
	 * @param args An object that parameters the action may need for its execution.
	 */
	void apply(ActionArgs args);
}
