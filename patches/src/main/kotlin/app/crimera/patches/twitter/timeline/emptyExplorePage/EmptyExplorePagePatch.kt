package app.crimera.patches.twitter.timeline.emptyExplorePage

import app.crimera.patches.twitter.misc.extension.sharedExtensionPatch
import app.crimera.patches.twitter.utils.Constants.COMPATIBILITY_X
import app.crimera.patches.twitter.utils.Constants.PATCHES_DESCRIPTOR
import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.instructions
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.Opcode

// androidx Fragment keeps its method names in X, so this is stable.
private object FragmentPerformViewCreatedFingerprint : Fingerprint(
    definingClass = "Landroidx/fragment/app/Fragment;",
    name = "performViewCreated",
    returnType = "V",
)

@Suppress("unused")
val emptyExplorePagePatch =
    bytecodePatch(
        name = "Empty explore page",
        description = "Explore page shows only the search bar (no trends, no Posts For You)",
    ) {
        compatibleWith(COMPATIBILITY_X)
        dependsOn(sharedExtensionPatch)

        execute {
            val method = FragmentPerformViewCreatedFingerprint.method
            val returnIndex = method.instructions.last { it.opcode == Opcode.RETURN_VOID }.location.index

            // v0 is a scratch register here, p0 is the Fragment.
            method.addInstructions(
                returnIndex,
                """
                iget-object v0, p0, Landroidx/fragment/app/Fragment;->mView:Landroid/view/View;
                invoke-static { p0, v0 }, $PATCHES_DESCRIPTOR/ExplorePage;->onViewCreated(Ljava/lang/Object;Landroid/view/View;)V
                """.trimIndent(),
            )
        }
    }
