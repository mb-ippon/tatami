package fr.ippon.tatami.repository.cassandra;

import fr.ippon.tatami.repository.MentionlineRepository;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static fr.ippon.tatami.config.ColumnFamilyKeys.MENTIONLINE_CF;

/**
 * Cassandra implementation of the Userline repository.
 * <p/>
 * Structure :
 * - Key : login
 * - Name : status Id
 * - Value : ""
 *
 * @author Julien Dubois
 */
@Repository
public class CassandraMentionlineRepository extends AbstractCassandraLineRepository implements MentionlineRepository {

    @Override
    public void addStatusToMentionline(String mentionedLogin, String statusId) {
        Mutator<String> mutator = HFactory.createMutator(keyspaceOperator, StringSerializer.get());
        mutator.insert(mentionedLogin, MENTIONLINE_CF, HFactory.createColumn(UUID.fromString(statusId),
                "", UUIDSerializer.get(), StringSerializer.get()));
    }

    @Override
    public List<String> getMentionline(String login, int size, String since_id, String max_id) {
        return getLineFromCF(MENTIONLINE_CF, login, size, since_id, max_id);
    }
}
