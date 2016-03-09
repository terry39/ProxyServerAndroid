package com.oplay.nohelper.assist.bolts;

/**
 * A function to be called after a task completes.
 *
 * If you wish to have the Task from a Continuation that does not return a Task be cancelled
 * then throw a {@link java.util.concurrent.CancellationException} from the Continuation.
 *
 * @see Task
 */
public interface Continuation<TTaskResult, TContinuationResult> {
	TContinuationResult then(Task<TTaskResult> task) throws Exception;
}
