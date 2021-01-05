package net.jzajic.graalvm.hack;

import static com.oracle.svm.core.snippets.KnownIntrinsics.readCallerStackPointer;
import static com.oracle.svm.core.snippets.KnownIntrinsics.readReturnAddress;

import java.util.ArrayList;

import org.graalvm.nativeimage.c.function.CodePointer;
import org.graalvm.word.Pointer;

import com.oracle.svm.core.annotate.NeverInline;
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;
import com.oracle.svm.core.code.FrameInfoQueryResult;
import com.oracle.svm.core.jdk.StackTraceUtils;
import com.oracle.svm.core.stack.JavaStackFrameVisitor;
import com.oracle.svm.core.stack.JavaStackWalker;

/**
 * Until merged commit https://github.com/oracle/graal/commit/b9a93e22cc138cbc5595baa312aee536d08bafa6
 * https://github.com/oracle/graal/pull/1484
 * to graalvm for slf4j to work
 * @author jzajic
 *
 */
public class GraalSubstitutions {

	@TargetClass(SecurityManager.class)
	@SuppressWarnings({"unused"})
	public static final class Target_java_lang_SecurityManager {
	    @Substitute
	    @NeverInline("Starting a stack walk in the caller frame")
	    protected Class<?>[] getClassContext() {
	        final CodePointer startIP = readReturnAddress();
	        final Pointer startSP = readCallerStackPointer();
	        return GraalSubstitutions.getClassContext(1, startSP, startIP);
	    }
	}
	
	private static final Class<?>[] NO_CLASSES = new Class<?>[0];
	
	public static Class<?>[] getClassContext(int skip, Pointer startSP, CodePointer startIP) {
    GetClassContextVisitor visitor = new GetClassContextVisitor(skip);
    JavaStackWalker.walkCurrentThread(startSP, startIP, visitor);
    return visitor.trace.toArray(NO_CLASSES);
	}
	
	public static class GetClassContextVisitor extends JavaStackFrameVisitor {
    private int skip;
    final ArrayList<Class<?>> trace;

    GetClassContextVisitor(final int skip) {
        trace = new ArrayList<>();
        this.skip = skip;
    }

    public boolean visitFrame(final FrameInfoQueryResult frameInfo) {
        if (skip > 0) {
            skip--;
        } else if (StackTraceUtils.shouldShowFrame(frameInfo, false, false)) {
            trace.add(frameInfo.getSourceClass());
        }
        return true;
    }
  }
  
}
